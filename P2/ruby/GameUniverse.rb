# encoding: UTF-8

require_relative 'Dice'
require_relative 'GameUniverseToUI'

module Deepspace
    class GameUniverse
        @@WIN = 10

        def initialize
            @currentStationindex = 0
            @turns = 0
            @dice = Dice.new

            @gameState = nil
            @currentStation = nil
            @spaceStations = nil
            @currentEnemy = nil
        end

        attr_reader :gameState

        def getUIVersion
            GameUniverseToUI.new(self)
        end

        def haveAWinner # : boolean
            @currentStation.nMedals >= @@WIN
        end

        def mountShieldBooster(i)
            if @gameState == INIT or @gameState == AFTERCOMBAT
                @currentStation.mountShieldBooster(i)
            end
        end

        def mountWeapon(i)
            if @gameState == INIT or @gameState == AFTERCOMBAT
                @currentStation.mountWeapon(i)
            end
        end

        def discardHangar
            if @gameState == INIT or @gameState == AFTERCOMBAT
                @currentStation.discardHangar(i)
            end
        end

        def discardShieldBooster(i)
            if @gameState == INIT or @gameState == AFTERCOMBAT
                @currentStation.discardShielBooster(i)
            end
        end

        def discardShieldBoosterInHangar(i)
            if @gameState == INIT or @gameState == AFTERCOMBAT
                @currentStation.discardShieldBoosterInHangar(i)
            end
        end

        def discardWeapon(i)
            if @gameState == INIT or @gameState == AFTERCOMBAT
                @currentStation.discardWeapon(i)
            end
        end

        def discardWeaponInHangar(i)
            if @gameState == INIT or @gameState == AFTERCOMBAT
                @currentStation.discardWeaponInHangar(i)
            end
        end
    end
end