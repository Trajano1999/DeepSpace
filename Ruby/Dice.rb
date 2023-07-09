# encoding: UTF-8

require_relative 'GameCharacter'

module Deepspace
	class Dice
		def initialize
			@NHANGARSPROB = 0.25
			@NSHIELDSPROB = 0.25
			@NWEAPONSPROB = 0.33
			@FIRSTSHOTPROB = 0.5
			@EXTRAEFFICIENCYPROB = 0.8
			@generator = Random.new
		end
		
		def to_s
			"NHANGARSPROB: #{@NHANGARSPROB}, NSHIELDSPROB: #{@NSHIELDSPROB}, NWEAPONSPROB: #{@NWEAPONSPROB}, FIRSTSHOTPROB: #{@FIRSTSHOTPROB}"
		end
		
		def initWithNHangars
			@generator.rand < @NHANGARSPROB ? 0 : 1
		end
		
		def initWithNWeapons
			case @generator.rand
			when 0.0 .. @NWEAPONSPROB
				1	
			when @NWEAPONSPROB .. 2 * @NWEAPONSPROB
				2
			else
				3
			end
		end
		
		def initWithNShields
			@generator.rand < @NSHIELDSPROB ? 0 : 1
		end
		
		def whoStarts (nPlayers)
			@generator.rand(nPlayers)
		end
		
		def firstShot
			@generator.rand < @FIRSTSHOTPROB ? GameCharacter::SPACESTATION : GameCharacter::ENEMYSTARSHIP
		end
		
		def spaceStationMoves (speed)
			@generator.rand < speed
		end

		def extraEfficiency # boolean
			@generator.rand < @EXTRAEFFICIENCYPROB
		end
	end
end

if $0 == __FILE__
	my_dice = Deepspace::Dice.new
	puts my_dice.to_s
end
