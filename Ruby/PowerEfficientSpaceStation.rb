# encoding: UTF-8

require_relative 'SpaceStation'
require_relative 'Transformation'
require_relative 'PowerEfficientSpaceStationToUI'

module Deepspace
    class PowerEfficientSpaceStation < SpaceStation
        @@EFFICIENCYFACTOR = 1.1

        def initialize(station) # station : SpaceStation
            super(station.name, SuppliesPackage.new(station.ammoPower, station.fuelUnits, station.shieldPower), \
            station.nMedals, station.pendingDamage, station.weapons, station.shieldBoosters, station.hangar)
        end

		def getUIversion
        	PowerEfficientSpaceStationToUI.new(self)
        end
        
        def to_s
        	getUIversion.to_s
      	end

        def fire
            super * @@EFFICIENCYFACTOR
        end

        def protection
            super * @@EFFICIENCYFACTOR
        end

        def setLoot(loot)
            super
            Transformation::NOTRANSFORM
        end
    end
end
