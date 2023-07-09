# encoding: UTF-8

require_relative 'WeaponType' # require './WeaponType'
require_relative 'Loot'
require_relative 'SuppliesPackage'
require_relative 'ShieldBooster'
require_relative 'Weapon'
require_relative 'Dice'

module Main
	class TestP1
		def self.main
			puts ""
			puts "----------------------- WeaponType --------------------------"
			puts ""
			una_instancia = Deepspace::WeaponType::Type.new(28)
			puts una_instancia.to_s
			puts una_instancia.power
			
			puts ""
			puts "----------------------- Loot --------------------------------"
			puts ""
			
			un_loot = Deepspace::Loot.new(1, 2, 3, 4, 5)
			puts un_loot.to_s
			puts un_loot.nShields
			
			puts ""
			puts "----------------- SuppliesPackage ---------------------------"
			puts ""
			
			un_supplies_package = Deepspace::SuppliesPackage.new(1, 2, 3)
			puts un_supplies_package.to_s
			otro_supplies_package = Deepspace::SuppliesPackage.newCopy(un_supplies_package)
			puts otro_supplies_package.to_s
			
			puts ""
			puts "------------------- ShieldBooster ---------------------------"
			puts ""
			
			un_shield_booster = Deepspace::ShieldBooster.new(1, 2, 0)
			puts un_shield_booster.to_s
			puts un_shield_booster.useIt
			otro_shield_booster = Deepspace::ShieldBooster.newCopy(un_shield_booster)
			puts otro_shield_booster.to_s
			
			puts ""
			puts "----------------------- Weapon ------------------------------"
			puts ""
			
			my_weapon = Deepspace::Weapon.new("Nombrecillo", Deepspace::WeaponType::PLASMA, 0)
			puts my_weapon.to_s
			puts my_weapon.power
			puts my_weapon.useIt
			otra_weapon = Deepspace::Weapon.newCopy(my_weapon)
			puts my_weapon.to_s
			puts otra_weapon.to_s
			
			puts ""
			puts "------------------------- Dice ------------------------------"
			puts ""
			
			my_dice = Deepspace::Dice.new
			puts my_dice.to_s
			puts my_dice.initWithNHangars
			puts my_dice.initWithNWeapons
			puts my_dice.initWithNShields
			puts my_dice.whoStarts(6)
			puts my_dice.firstShot
			puts my_dice.spaceStationMoves(0.75)
			
			puts ""
			puts "---------------------- Ejecuciones --------------------------"
			puts ""
			
			mi_dice = Deepspace::Dice.new
			data = {:initWithNHangars => {0 => 0, 1 => 0}, :initWithNWeapons => {1 => 0, 2 => 0, 3 => 0}, :initWithNShields => {0 => 0, 1 => 0}, :whoStarts => {}, :firstShot => {Deepspace::GameCharacter::SPACESTATION => 0, Deepspace::GameCharacter::ENEMYSTARSHIP => 0}, :spaceStationMoves => {true => 0, false => 0}}
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
			puts
		end
	end
end

Main::TestP1.main
