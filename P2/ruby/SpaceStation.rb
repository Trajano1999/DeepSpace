#encoding: UTF-8

require_relative 'SpaceStationToUI'

module Deepspace
    class SpaceStation
        @@MAXFUEL = 100
        @@SHIELDLOSSPERUNITSHOT = 0.1

        def initialize (n, supplies) # SpaceStation(n : string, supplies : SuppliesPackage)
			@name = n
			@ammoPower = supplies.ammoPower
            @fuelUnits = supplies.fuelUnits
			@shieldPower = supplies.shieldPower			
			
			@nMedals = 0
            @pendingDamage = nil # : Damage
            @weapons = Array.new # : Array<Weapon>
            @shieldBoosters = Array.new # : ShieldBooster
            @hangar = nil # : Hangar
		end
		
		attr_reader :name, :ammoPower, :fuelUnits, :shieldPower, :nMedals, :pendingDamage, :weapons, :shieldBoosters, :hangar

		def getUIversion
			SpaceStationToUI.new(self)
		end

		def to_s
			"MAXFUEL: #{@@MAXFUEL}, SHIELDLOSSPERUNITSHOT: #{@@SHIELDLOSSPERUNITSHOT}, ammoPower: #{@ammoPower}, fuelUnits: #{@fuelUnits}, name: #{@name}, nMedals: #{@nMedals}, shieldPower: #{@shieldPower}, pendingDamage: [#{@pendingDamage}], weapons: {#{@weapons}}, shieldBoosters: {#{@shieldBoosters}}, hangar: [#{@hangar}]"
		end

        def assignFuelValue (f)
        	@fuelUnits = f <= @@MAXFUEL ? f : @@MAXFUEL
        end

		def cleanPendingDamage
			if @pendingDamage != nil or @pendingDamage.hasNoEffect
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
			if @hangar == nil 
				@hangar = h
			end
		end
		
		def discardHangar
			@hangar = nil
		end
		
		def receiveSupplies(s) # s: SuppliesPackage
			@fuelUnits += s.fuelUnits
			@shieldPower += s.shieldPower
			@ammoPower += s.ammoPower
		end
		
		def setPendingDamage(d) # setPendingDamage(d : Damage) : void
			@pendingDamage = d.adjust(@weapons, @shieldBoosters)
		end

		def mountWeapon (i) # mountWeapon(i : int) : void
			new_weapon = @hangar.weapons.delete_at(i) unless hangar.nil?
			@weapons << new_weapon unless new_weapon.nil?
		end

		def mountShieldBooster(i) # mountShieldBooster(i : int) : void
			new_shield_booster = @hangar.shieldBoosters.delete_at(i) unless hangar.nil?
			@shieldBoosters << new_shield_booster unless new_shield_booster.nil?
		end

		def discardWeaponInHangar (i)
			if @hangar != nil
				@hangar.removeWeapon(i)
			end
		end

		def discardShieldBoosterInHangar (i) 
			if @hangar != nil
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
			@pendingDamage == nil or @pendingDamage.hasNoEffect
		end

		def cleanUpMountedItems
			@weapons.delete_if {|x| x.uses == 0} unless @weapons.nil?
			@shieldBoosters.delete_if {|x| x.uses == 0} unless @shieldBoosters.nil?
		end

		# Especificación de acceso a métodos privados
		private :assignFuelValue, :cleanPendingDamage
    end
end

=begin
+discardShieldBooster(i : int) : void
+discardWeapon(i : int) : void
+fire() : float
+protection() : float
+receiveShot(shot : float) : ShotResult
+setLoot(loot : Loot) : void
=end

if $0 == __FILE__
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
end
