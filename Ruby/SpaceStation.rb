#encoding: UTF-8

require_relative 'SpaceStationToUI'
require_relative 'ShotResult'
require_relative 'CardDealer'
require_relative 'Transformation'
require_relative 'SuppliesPackage'
require_relative 'Hangar'

module Deepspace
    class SpaceStation
        @@MAXFUEL = 100
        @@SHIELDLOSSPERUNITSHOT = 0.1

        def initialize (n, supplies, nMedals = 0, pendingDamage = nil, weapons = Array.new, shieldBoosters = Array.new, hangar = nil) # SpaceStation(n : string, supplies : SuppliesPackage)
			@name = n

			@ammoPower = supplies.ammoPower
            @fuelUnits = supplies.fuelUnits
			@shieldPower = supplies.shieldPower

			@nMedals = nMedals
			@pendingDamage = pendingDamage
			@weapons = weapons
			@shieldBoosters = shieldBoosters
			@hangar = hangar
		end
		
		attr_reader :name, :ammoPower, :fuelUnits, :shieldPower, :nMedals, :pendingDamage, :weapons, :shieldBoosters, :hangar


		def self.newCopy(station) # station : SpaceStation
			sup = Deepspace::SuppliesPackage.new(station.ammoPower, station.fuelUnits, station.shieldPower)
			new(station.name, sup, station.nMedals, station.pendingDamage.class.newCopy(station.pendingDamage), Array.new(station.weapons), Array.new(station.shieldBoosters), Hangar.newCopy(station.hangar))
		end

		def getUIversion
			SpaceStationToUI.new(self)
		end

		def to_s
			getUIversion.to_s 
		end

        def assignFuelValue (f)
        	@fuelUnits = f <= @@MAXFUEL ? f : @@MAXFUEL
        end

		def cleanPendingDamage
			if @pendingDamage != nil && @pendingDamage.hasNoEffect
				@pendingDamage = nil
			end
		end
		
		def receiveWeapon(w) # receiveWeapon(w : Weapon) : boolean
			if @hangar != nil
				@hangar.addWeapon(w) # true si tiene éxito, false si no
			else
				false
			end
		end
		
		def receiveShieldBooster(s)
			if @hangar != nil
				@hangar.addShieldBooster(s) # true si tiene éxito, false si no
			else
				false
			end
		end
		
		def receiveHangar(h)
			if @hangar.nil? 
				@hangar = h
			end
		end
		
		def discardHangar
			@hangar = nil
		end
		
		def receiveSupplies(s) # s: SuppliesPackage
			assignFuelValue(@fuelUnits + s.fuelUnits)
			@shieldPower += s.shieldPower
			@ammoPower += s.ammoPower
		end
		
		def setPendingDamage(d) # setPendingDamage(d : Damage) : void
			@pendingDamage = d.adjust(@weapons, @shieldBoosters)
		end

		def mountWeapon(i) # mountWeapon(i : int) : void
			if 0 <= i && i < @hangar.weapons.length
				new_weapon = @hangar.weapons.delete_at(i) unless hangar.nil?
				@weapons << new_weapon unless new_weapon.nil?
			end
		end

		def mountShieldBooster(i) # mountShieldBooster(i : int) : void
			if 0 <= i && i < @hangar.shieldBoosters.length
				new_shield_booster = @hangar.shieldBoosters.delete_at(i) unless hangar.nil?
				@shieldBoosters << new_shield_booster unless new_shield_booster.nil?
			end
		end

		def discardWeaponInHangar (i)
			if @hangar != nil && 0 <= i && i < @hangar.weapons.length
				@hangar.removeWeapon(i)
			end
		end

		def discardShieldBoosterInHangar (i) 
			if @hangar != nil && 0 <= i && i < @hangar.shieldBoosters.length
				@hangar.removeShieldBooster(i)
			end
		end

		def getSpeed
			@fuelUnits / @@MAXFUEL
		end

		def move
			@fuelUnits = @fuelUnits >= getSpeed ? @fuelUnits - getSpeed : 0
		end

		def validState
			@pendingDamage.nil? || @pendingDamage.hasNoEffect
		end

		def cleanUpMountedItems
			@weapons.delete_if {|x| x.uses == 0} unless @weapons.nil?
			@shieldBoosters.delete_if {|x| x.uses == 0} unless @shieldBoosters.nil?
		end

		def fire
			factor = 1.0
			@weapons.each {|w| factor *= w.useIt} unless @weapons.nil?
			@ammoPower*factor
		end

		def protection
			factor = 1.0
			@shieldBoosters.each {|s| factor *= s.useIt} unless @shieldBoosters.nil?
			@shieldPower*factor
		end

		def receiveShot(shot) # receiveShot(shot : float) : ShotResult
			myProtection = protection
			
			if myProtection >= shot
				@shieldPower = [0.0, @shieldPower - @@SHIELDLOSSPERUNITSHOT*shot].max
				ShotResult::RESIST
			else
				shieldPower = 0.0
				ShotResult::DONOTRESIST
			end
		end

		def setLoot(loot) # setLoot(loot : Loot) : Transformation
			dealer = CardDealer.instance
			h = loot.nHangars
			
			if h > 0
				hangar = dealer.nextHangar
				receiveHangar(hangar)
			end

			elements = loot.nSupplies
			for i in 1..elements
				sup = dealer.nextSuppliesPackage
				receiveSupplies(sup)
			end

			elements = loot.nWeapons
			for i in 1..elements
				weap = dealer.nextWeapon
				receiveWeapon(weap)
			end

			elements = loot.nShields
			for i in 1..elements
				sh = dealer.nextShieldBooster
				receiveShieldBooster(sh)
			end

			medals = loot.nMedals
			@nMedals += medals

			if loot.spaceCity
				Transformation::SPACECITY
			elsif loot.efficient
				Transformation::GETEFFICIENT
			else
				Transformation::NOTRANSFORM
			end
		end

		def discardWeapon(i) # discardWeapon(i : int) : void
			size = @weapons.length

			if i >= 0 && i < size
				w = @weapons.delete_at(i)
				if @pendingDamage != nil
					@pendingDamage.discardWeapon(w)
					cleanPendingDamage
				end
			end
		end

		def discardShieldBooster(i) # discardShieldBooster(i : int) : void
			size = @shieldBoosters.length

			if i >= 0 && i < size
				@shieldBoosters.delete_at(i)
				if @pendingDamage != nil
					@pendingDamage.discardShieldBooster	# He quitado el parámetro de discardShieldBooster
					cleanPendingDamage
				end
			end
		end

		# Especificación de acceso a métodos privados
		private :assignFuelValue, :cleanPendingDamage
    end
end

if $0 == __FILE__
=begin	
	require_relative 'SuppliesPackage'
	require_relative 'Weapon'
	require_relative 'WeaponType'
	require_relative 'Hangar'
	require_relative 'Damage'

	my_space_station = Deepspace::SpaceStation.new("Nombre de mi estación", Deepspace::SuppliesPackage.new(27, 28, 29))
	puts my_space_station.to_s

	my_space_station.assignFuelValue(100)
	puts my_space_station.to_s

	puts
	d = Deepspace::Damage.newNumericWeapons(3,3)
	puts d.to_s
	my_space_station.setPendingDamage(d)
	puts my_space_station.to_s
	my_space_station.cleanPendingDamage
	puts my_space_station.to_s

	puts
	my_space_station.receiveHangar(Deepspace::Hangar.new(12))
	puts my_space_station.to_s
	my_space_station.receiveWeapon(Deepspace::Weapon.new(4, 5, 6))
	puts my_space_station.to_s
	my_space_station.receiveSupplies(Deepspace::SuppliesPackage.new(27, 28, 29))
	puts my_space_station.to_s

	puts
	my_space_station.setPendingDamage(Deepspace::Damage.newSpecificWeapons(Array.new(3){Deepspace::WeaponType::MISSILE}, 1))
	puts my_space_station.to_s

	puts "\nCosa que quiero comprobar\n\n"
	puts my_space_station.mountWeapon(0)
	puts my_space_station.to_s

	puts my_space_station.mountShieldBooster(3)
	puts my_space_station.to_s

	puts my_space_station.discardWeaponInHangar(0)
	puts my_space_station.to_s
	puts my_space_station.discardShieldBoosterInHangar(5)
	puts my_space_station.to_s

	puts my_space_station.getSpeed
	puts my_space_station.move
	puts my_space_station.validState
	my_space_station.cleanUpMountedItems
	puts my_space_station.to_s

=end

	require_relative 'SuppliesPackage'
	require_relative 'Weapon'
	require_relative 'WeaponType'
	require_relative 'Hangar'
	require_relative 'Damage'

	my_space_station = Deepspace::SpaceStation.new("Nombre de mi estación", Deepspace::SuppliesPackage.new(27, 28, 29))
	puts my_space_station

	puts
	my_space_station.receiveHangar(Deepspace::Hangar.new(12))
	puts my_space_station.to_s
	my_space_station.receiveWeapon(Deepspace::Weapon.new(4, Deepspace::WeaponType::LASER, 6))
	puts my_space_station.to_s
	my_space_station.receiveSupplies(Deepspace::SuppliesPackage.new(27, 28, 29))
	puts my_space_station.to_s

	my_space_station.mountWeapon(0)
	puts my_space_station
	puts my_space_station.fire
	puts my_space_station.protection
	puts my_space_station.receiveShot(1.5)
	my_space_station.setLoot(Deepspace::Loot.new(1,1,1,1,1))
	puts my_space_station

	my_space_station.discardWeapon(0)
	my_space_station.discardShieldBooster(0)
end
