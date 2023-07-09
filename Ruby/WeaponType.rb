# encoding: UTF-8

module Deepspace
	module WeaponType
		class Type
			def initialize (p) # WeaponType(p : float)
				@power = p
			end

			attr_reader :power # Observador de @power, equivalente a un método que lo devuelva
			
			def to_s
				case @power
				when 2.0
					"LASER"
				when 3.0
					"MISSILE"
				when 4.0
					"PLASMA"
				end
			end
		end
		
		LASER = Type.new(2.0)
		MISSILE = Type.new(3.0)
		PLASMA = Type.new(4.0)
	end
end

if $0 == __FILE__
	puts Deepspace::WeaponType::Type.new(2).to_s
end