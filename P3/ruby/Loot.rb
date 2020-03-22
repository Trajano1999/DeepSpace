# encoding: UTF-8

require_relative 'LootToUI'

module Deepspace
	class Loot
		def initialize (nSu, nWe, nSh, nHa, nMe) # Loot(nsu : int, nw : int, nsh : int, nh : int, nm : int)
			@nSupplies = nSu
			@nWeapons = nWe
			@nShields = nSh
			@nHangars = nHa
			@nMedals = nMe
		end
		
		attr_reader :nSupplies, :nWeapons, :nShields, :nHangars, :nMedals

		def self.newCopy (l)
			new(l.nSupplies, l.nWeapons, l.nShields, l.nHangars, l.nMedals)
		end

		def getUIversion
			LootToUI.new(self)
		end

		def to_s
			getUIversion.to_s
		end
		
	end
end

if $0 == __FILE__
	puts Deepspace::Loot.new(1, 2, 3, 4, 5)
	puts Deepspace::Loot.new(1, 2, 3, 4, 5).getUIversion
end