# encoding: UTF-8

require_relative 'PowerEfficientSpaceStation'
require_relative 'Dice'
require_relative 'BetaPowerEfficientSpaceStationToUI'

module Deepspace
    class BetaPowerEfficientSpaceStation < PowerEfficientSpaceStation
        @@EXTRAEFFICIENCY = 1.2

        def initialize(station) # station : SpaceStation
            super
            @dice = Dice.new
        end
        
        def getUIversion
        	BetaPowerEfficientSpaceStationToUI.new(self)
        end
        
        def to_s
        	getUIversion.to_s
      	end

        def fire
            @dice.extraEfficiency ? super * @@EXTRAEFFICIENCY : super
        end
    end
end
