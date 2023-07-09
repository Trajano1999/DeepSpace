# encoding: UTF-8

require_relative 'Dice'
require_relative 'GameUniverseToUI'
require_relative 'CardDealer'
require_relative 'SpaceStation'
require_relative 'Loot'
require_relative 'CombatResult' 
require_relative 'GameCharacter'
require_relative 'ShotResult'
require_relative 'GameState'
require_relative 'GameStateController'
require_relative 'BetaPowerEfficientSpaceStation'
require_relative 'PowerEfficientSpaceStation'
require_relative 'SpaceCity'

module Deepspace
    class GameUniverse
        @@WIN = 10

        def initialize
            @currentStationIndex = 0
            @turns = 0
            @dice = Dice.new

            @gameState = GameStateController.new # GameStateController
            @currentStation = nil # SpaceStation
            @spaceStations = Array.new # ArrayList<SpaceStation>
            @currentEnemy = nil # EnemyStarShip

            @haveSpaceCity = false
        end

        def state
            @gameState.state
        end

        def getUIversion
            GameUniverseToUI.new(@currentStation, @currentEnemy) unless @currentEnemy == nil && @currentStation == nil
        end

        def to_s
            getUIversion.to_s
        end

        def haveAWinner # : boolean
            @currentStation.nMedals >= @@WIN unless @currentStation == nil
        end

        def mountWeapon(i)
            if @gameState != nil && (@gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT)
                @currentStation.mountWeapon(i)
            end
        end

        def mountShieldBooster(i)
            if @gameState != nil && (@gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT)
                @currentStation.mountShieldBooster(i)
            end
        end

        def discardHangar
            if @gameState != nil && (@gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT)
                @currentStation.discardHangar
            end
        end

        def discardShieldBooster(i)
            if @gameState != nil && (@gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT)
                @currentStation.discardShieldBooster(i)
            end
        end

        def discardShieldBoosterInHangar(i)
            if @gameState != nil && (@gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT)
                @currentStation.discardShieldBoosterInHangar(i)
            end
        end

        def discardWeapon(i)
            if @gameState != nil && (@gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT)
                @currentStation.discardWeapon(i)
            end
        end

        def discardWeaponInHangar(i)
            if @gameState != nil && (@gameState.state == GameState::INIT || @gameState.state == GameState::AFTERCOMBAT)
                @currentStation.discardWeaponInHangar(i)
            end
        end

        def init(names) # init(names : string[]) : void
            state = @gameState.state unless @gameState == nil
            if state == GameState::CANNOTPLAY
                dealer = CardDealer.instance
                for name in names
                    supplies = dealer.nextSuppliesPackage
                    station = SpaceStation.new(name, supplies)
                    @spaceStations.push(station)
                    nh = @dice.initWithNHangars
                    nw = @dice.initWithNWeapons
                    ns = @dice.initWithNShields
                    lo = Loot.new(0,nw,ns,nh,0)
                    station.setLoot(lo)
                end
                @currentStationIndex = @dice.whoStarts(names.length)
                @currentStation = @spaceStations[@currentStationIndex]
                @currentEnemy = dealer.nextEnemy
                            
                @gameState.next(@turns, @spaceStations.length)
            end
        end

        def nextTurn # nextTurn() : boolean
            # state = @gameState.state unless @gameState == nil # Innecesario
            if state == GameState::AFTERCOMBAT
                stationState = @currentStation.validState
                if stationState
                    @currentStationIndex = (@currentStationIndex+1) % @spaceStations.length
                    @turns += 1

                    @currentStation = @spaceStations[@currentStationIndex]
                    @currentStation.cleanUpMountedItems
                    dealer = CardDealer.instance
                    @currentEnemy = dealer.nextEnemy
                    @gameState.next(@turns, @spaceStations.length)
                    return true
                end
            end
            false
        end

        def combat # combat() : CombatResult
            if state == GameState::BEFORECOMBAT || state == GameState::INIT
                combatGo(@currentStation, @currentEnemy)
            else
                CombatResult::NOCOMBAT
            end
        end

        def combatGo(station, enemy) # combatGo(station : SpaceStation, enemy : EnemyStarShip) : CombatResult
            ch = @dice.firstShot
            if ch == GameCharacter::ENEMYSTARSHIP
                fire = enemy.fire
                result = station.receiveShot(fire)
                
                if result == ShotResult::RESIST
                    fire = station.fire
                    result = enemy.receiveShot(fire)
                    enemyWins = (result == ShotResult::RESIST)
                else
                    enemyWins = true
                end
            else
                fire = station.fire
                result = enemy.receiveShot(fire)
                enemyWins = (result == ShotResult::RESIST)
            end

            if enemyWins
                s = station.getSpeed
                moves = @dice.spaceStationMoves(s)

                if !moves
                    damage = enemy.damage
                    station.setPendingDamage(damage)
                    combatResult = CombatResult::ENEMYWINS
                else
                    station.move
                    combatResult = CombatResult::STATIONESCAPES
                end
            else
                aLoot = enemy.loot
                trans = station.setLoot(aLoot) # hay que hacerlo antes de la eventual conversión
                                        # puts "Enemigo en CombatGo:"
                                        # puts enemy

                if trans == Transformation::GETEFFICIENT
                    makeStationEfficient
                    combatResult = CombatResult::STATIONWINSANDCONVERTS
                                        # puts "GameUniverse::combatGo: STATIONWINSANDCONVERTS"
                elsif trans == Transformation::SPACECITY
                    createSpaceCity
                    combatResult = CombatResult::STATIONWINSANDCONVERTS
                                        # puts "GameUniverse::combatGo: STATIONWINSANDCONVERTS"
                elsif trans == Transformation::NOTRANSFORM
                                        # puts "GameUniverse::combatGo: STATIONWINS (no trans)"
                    combatResult = CombatResult::STATIONWINS
                end
            end

            @gameState.next(@turns, @spaceStations.length)
            combatResult
        end

        def makeStationEfficient
            if @dice.extraEfficiency
                @currentStation = BetaPowerEfficientSpaceStation.new(@currentStation)
            else
                @currentStation = PowerEfficientSpaceStation.new(@currentStation)
            end
            @spaceStations[@currentStationIndex] = @currentStation
        end

        def createSpaceCity
            if @haveSpaceCity == false
                collaborators = Array.new(@spaceStations) # para no modificar @spaceStation
                collaborators.delete(@currentStation)
                @currentStation = SpaceCity.new(@currentStation, collaborators)
                @spaceStations[@currentStationIndex] = @currentStation
                @haveSpaceCity = true
            end
        end
    end # class
end # module

if $0 == __FILE__
    g = Deepspace::GameUniverse.new
    puts g
    g.haveAWinner
    g.mountWeapon(0)
    g.mountShieldBooster(0)

    g.discardHangar
    g.discardWeapon(0)
    g.discardWeaponInHangar(0)
    g.discardShieldBooster(0)
    g.discardShieldBoosterInHangar(0)
    g.nextTurn
    g.combat2
end
