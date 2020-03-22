# encoding: UTF-8

require_relative 'LootToUI'

module Deepspace
	class Loot
		def initialize (nSu, nWe, nSh, nHa, nMe, ef=false, city=false) # Loot(nsu : int, nw : int, nsh : int, nh : int, nm : int, ef : boolean, city : boolean)
			@nSupplies = nSu
			@nWeapons = nWe
			@nShields = nSh
			@nHangars = nHa
			@nMedals = nMe

			@efficient = ef
			@spaceCity = city
		end
		
		attr_reader :nSupplies, :nWeapons, :nShields, :nHangars, :nMedals, :efficient, :spaceCity

		def self.newCopy (l)
			new(l.nSupplies, l.nWeapons, l.nShields, l.nHangars, l.nMedals, l.efficient, l.spaceCity)
		end

		def getUIversion
			LootToUI.new(self)
		end

		def to_s
			getUIversion.to_s
		end
		
	end # class
end # module

if $0 == __FILE__
	puts Deepspace::Loot.new(1, 2, 3, 4, 5)
	puts Deepspace::Loot.new(1, 2, 3, 4, 5).getUIversion
end
