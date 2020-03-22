# encoding: UTF-8

require_relative 'GameCharacter'

module Deepspace
	class Dice
		def initialize
			@NHANGARSPROB = 0.25
			@NSHIELDSPROB = 0.25
			@NWEAPONSPROB = 0.33
			@FIRSTSHOTPROB = 0.5
		end
		
		def to_s
			"NHANGARSPROB: #{@NHANGARSPROB}, NSHIELDSPROB: #{@NSHIELDSPROB}, NWEAPONSPROB: #{@NWEAPONSPROB}, FIRSTSHOTPROB: #{@FIRSTSHOTPROB}"
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
end

if $0 == __FILE__
	my_dice = Deepspace::Dice.new
	puts my_dice.to_s
end
