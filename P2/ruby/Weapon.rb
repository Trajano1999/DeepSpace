# encoding: UTF-8

require_relative 'WeaponType'
require_relative 'WeaponToUI'

module Deepspace
	class Weapon
		def initialize (n, t, u) # Weapon(n : string, t : WeaponType, u : int)
			@name = n
			@type = t
			@uses = u
		end
		
		attr_reader :name
		attr_reader :type
		attr_reader :uses
		
		def self.newCopy (other)
			new(other.name, other.type, other.uses)
		end

		def getUIVersion
			WeaponToUI.new(self)
		end
		
		def to_s
			"name: #{@name}, type: [#{@type}], uses: #{@uses}"
		end

		def power
			@type.power
		end
		
		def useIt
			if @uses > 0
				@uses -= 1
				power
			else
				1.0
			end
		end
	end
end

if $0 == __FILE__
	puts Deepspace::Weapon.new(1,2,3).to_s
	puts Deepspace::Weapon.new(1, Deepspace::WeaponType::LASER, 3).getUIVersion.to_s
end