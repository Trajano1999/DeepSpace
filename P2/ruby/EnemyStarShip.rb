# encoding: UTF-8

require_relative 'EnemyToUI'
require_relative 'ShotResult'
require_relative 'Loot'
require_relative 'Damage'

module Deepspace
	class EnemyStarShip
		def initialize(n, a, s, l, d) # EnemyStarShip(n : string, a : float, s : float, l : Loot, d : Damage)
			@name = n
			@ammoPower = a
			@shieldPower = s
			@loot = l
			@damage = d
		end

		attr_reader :name, :ammoPower, :shieldPower, :loot, :damage

		def self.newCopy(e)
			new(e.name, e.ammoPower, e.shieldPower, Loot.newCopy(e.loot), Damage.newCopy(e.damage))
		end

		def getUIVersion
			EnemyToUI.new(self)
		end

		def to_s
			"name: #{@name}, ammoPower: #{@ammoPower}, shieldPower: #{@shieldPower}, loot: [#{@loot}], damage: [#{@damage}]"
		end

		def fire
			@ammoPower
		end

		def protection
			@shieldPower
		end

		def receiveShot(shot) # receiveShot(shot : float) : ShotResult
			shieldPower < shot ? ShotResult::DONOTRESIST : ShotResult::RESIST
		end
		
	end
end

if $0 == __FILE__
	ey = Deepspace::EnemyStarShip.new("hola",1,2,Deepspace::Loot.new(10,11,12,13,14),Deepspace::Damage.newNumericWeapons(3,2))
	puts ey.to_s

	e2 = Deepspace::EnemyStarShip.newCopy(ey)
	puts e2.to_s

	#puts ey.getUIVersion.to_s

	print ey.fire
	print " "
	puts ey.protection

	puts ey.shieldPower
	puts ey.receiveShot(2.5)
end