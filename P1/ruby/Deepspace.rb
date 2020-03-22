# encoding: UTF-8

module Deepspace
	module CombatResult
		ENEMYWINS = :enemywins
		NOCOMBAT = :nocombat
		STATIONSCAPES = :stationscapes
		STATIONSWINS = :stationswins
	end
	
	module GameCharacter
		ENEMYSTARSHIP = :enemystarship
		SPACESTATION = :spacestation
	end
	
	module ShotResult
		DONOTRESIST = :donotresist
		RESIST = :resists
	end
	
	module WeaponType
		class Type
			def initialize (p)
				@power = p
			end
			
			attr_reader :power # Observador de @power, equivalente a un método que lo devuelva
		end
		
		LASER = Type.new(2.0)
		MISSILE = Type.new(3.0)
		PLASMA = Type.new(4.0)
	end
	
	class Loot
		def initialize (nSu, nWe, nSh, nHa, nMe)
			@nSupplies = nSu
			@nWeapons = nWe
			@nShields = nSh
			@nHangars = nHa
			@nMedals = nMe
		end
		
		attr_reader :nSupplies
		attr_reader :nWeapons
		attr_reader :nShields
		attr_reader :nHangars
		attr_reader :nMedals
	end
	
	class SuppliesPackage
		def initialize (ap, fu, sp)
			@ammoPower = ap
			@fuelUnits = fu
			@shieldPower = sp
		end
		
		attr_reader :ammoPower
		attr_reader :fuelUnits
		attr_reader :shieldPower
		
		def self.newCopy (other) # Constructor de copia (es un método de clase)
			new(other.ammoPower, other.fuelUnits, other.shieldPower)
		end
	end
	
	class ShieldBooster
		def initialize (n, b, u)
			@name = n
			@boost = b
			@uses = u
		end
		
		attr_reader :name  # ¿Debería ser privado?
		attr_reader :boost
		attr_reader :uses
		
		def self.newCopy (other)
			new(other.name, other.boost, other.uses)
		end
		
		def useIt
			if @uses > 0
				@uses -= 1
				return @boost
			else
				return 1.0
			end
		end
	end
	
	class Weapon
		def initialize (n, t, u)
			@name = n
			@type = t
			@uses = u
		end
		
		attr_reader :name
		attr_reader :boost
		attr_reader :uses
		
		def self.newCopy (other)
			new(other.name, other.boost, other.uses)
		end
		
		def power
			@type.power
		end
		
		def useIt
			if @uses > 0
				@uses -= 1
				return power
			else
				return 1.0
			end
		end
	end
	
	class Dice
		def initialize
			@NHANGARSPROB = 0.25
			@NSHIELDSPROB = 0.25
			@NWEAPONSPROB = 0.33
			@FIRSTSHOTPROB = 0.5
		end
		
		# Para los siguientes métodos se usan las funciones rand
		# rand -> genera un número "real" entre 0.0 y 1.0
		# rand(x) -> genera un entero entre 0 y x (excluido x)
		
		def initWithNHangars
			rand < @NHANGARSPROB ? 0 : 1
		end
		
		def initWithNWeapons
			case rand
			when 0.0 .. @NWEAPONSPROB
				1	
			when @NWEAPONSPROB .. 2 * @NWEAPONSPROB
				2
			else
				3
			end
		end
		
		def initWithNShields
			rand < @NSHIELDSPROB ? 0 : 1
		end
		
		def whoStarts (nPlayers)
			rand(nPlayers)
		end
		
		def firstShot
			rand < @FIRSTSHOTPROB ? GameCharacter::SPACESTATION : GameCharacter::ENEMYSTARSHIP
		end
		
		def spaceStationMoves (speed)
			rand < speed
		end
	end

	class TestP1
		def self.main			
			una_instancia = WeaponType::Type.new(28)
			puts una_instancia.inspect
			puts una_instancia.power

			un_loot = Loot.new(1, 2, 3, 4, 5)
			puts un_loot.inspect
			#puts un_loot.nShields

			un_supplies_package = SuppliesPackage.new(1, 2, 3)
			puts un_supplies_package.inspect
			otro_supplies_package = SuppliesPackage.newCopy(un_supplies_package)
			puts otro_supplies_package.inspect

			un_shield_booster = ShieldBooster.new(1, 2, 0)
			puts un_shield_booster.inspect
			puts un_shield_booster.useIt
			otro_shield_booster = ShieldBooster.newCopy(un_shield_booster)
			puts otro_shield_booster.inspect

			my_weapon = Weapon.new("Nombrecillo", WeaponType::PLASMA, 0)
			puts my_weapon.inspect
			puts my_weapon.power
			puts my_weapon.useIt
			otra_weapon = Weapon.newCopy(my_weapon)
			puts otra_weapon.inspect

			my_dice = Dice.new
			puts my_dice.inspect
			puts my_dice.initWithNHangars
			puts my_dice.initWithNWeapons
			puts my_dice.initWithNShields
			puts my_dice.whoStarts(6)
			puts my_dice.firstShot
			puts my_dice.spaceStationMoves(0.75)
			
			mi_dice = Dice.new
			data = {:initWithNHangars => {0 => 0, 1 => 0}, :initWithNWeapons => {1 => 0, 2 => 0, 3 => 0}, :initWithNShields => {0 => 0, 1 => 0}, :whoStarts => {}, :firstShot => {GameCharacter::SPACESTATION => 0, GameCharacter::ENEMYSTARSHIP => 0}, :spaceStationMoves => {true => 0, false => 0}}
			# Completar data[:whoStarts] con las posibles claves
			
			ngamers = 6
			for i in 0..ngamers-1
				data[:whoStarts][i] = 0
			end
			
			# Calcular los datos de ejecución
			for i in 0..99
				data[:initWithNHangars][mi_dice.initWithNHangars] += 1
				data[:initWithNWeapons][mi_dice.initWithNWeapons] += 1
				data[:initWithNShields][mi_dice.initWithNShields] += 1
				data[:whoStarts][mi_dice.whoStarts(ngamers)] += 1
				data[:firstShot][mi_dice.firstShot] += 1
				data[:spaceStationMoves][mi_dice.spaceStationMoves(0.5)] += 1
			end
			
			puts "\nTras ejecutar cada método 100 veces, obtenemos lo siguiente: "
			for d in data
				print "Metodo "
				puts d
			end
		end
	end
end

Deepspace::TestP1.main
