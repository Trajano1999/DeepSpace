#encoding: UTF-8

require_relative 'SpaceStation'
require_relative 'SuppliesPackage'
require_relative 'Transformation'
require_relative 'SpaceCityToUI'

module Deepspace
	class SpaceCity < SpaceStation
		
		def initialize (base, rest) # SpaceCity(base : SpaceStation, rest : SpaceStation[])
			super(base.name, SuppliesPackage.new(base.ammoPower, base.fuelUnits, base.shieldPower), \
				base.nMedals, base.pendingDamage, base.weapons, base.shieldBoosters, base.hangar)
			@base = base
			@collaborators = rest
		end
		
		attr_reader :collaborators
		
		def getUIversion
        	SpaceCityToUI.new(self)
        end
        
        def to_s
        	getUIversion.to_s
      	end
		
		def fire
			suma = 0
			for c in @collaborators
				suma += c.fire
			end
			suma + super
		end

		def protection
			suma = 0
			for c in @collaborators
				suma += c.protection
			end
			suma + super
		end
		
		def setLoot(loot)
			super
			Transformation::NOTRANSFORM
		end
	end
end
