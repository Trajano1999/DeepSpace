# encoding: UTF-8

require_relative 'HangarToUI'

module Deepspace
	class Hangar
		def initialize (capacity) # Hangar(capacity : int)
			@maxElements = capacity
			@weapons = Array.new
			@shieldBoosters = Array.new
		end
		
		attr_reader :maxElements
		attr_accessor :weapons, :shieldBoosters
		
		def self.newCopy (h)
			copy = new(h.maxElements)
			copy.weapons = Array.new(h.weapons)
			copy.shieldBoosters = Array.new(h.shieldBoosters)
			copy
		end
		
		def getUIversion
			HangarToUI.new(self)
		end
		
		def to_s
			getUIversion.to_s 
		end

		def spaceAvailable
			@weapons.length + @shieldBoosters.length < @maxElements
		end
		
		def addWeapon (w)
			if spaceAvailable
				@weapons << w # equivalente a @weapons.push(w)
				true
			else
				false
			end
		end
		
		def addShieldBooster (w)
			if spaceAvailable
				@shieldBoosters << w
				true
			else
				false
			end
		end
		
		def removeShieldBooster (s)	# removeShieldBooster(s : int) : ShieldBooster
			@shieldBoosters.delete_at(s) # Devuelve el ShieldBooster borrado
		end

		def removeWeapon (w) # removeWeapon(w : int) : Weapon
			@weapons.delete_at(w) # Devuelve el ShieldBooster borrado
		end
		
		# Especificación de acceso a métodos privados
		private :spaceAvailable
	end
end

if $0 == __FILE__
	require_relative 'Weapon'

	h = Deepspace::Hangar.new(4)
	puts h
	h2 = Deepspace::Hangar.newCopy(h)
	puts h2
	puts h.getUIversion
	# puts h.spaceAvailable
	puts h.addWeapon(Deepspace::Weapon.new("Pistolilla", Deepspace::WeaponType::MISSILE, 7))
	puts h
	puts h.removeWeapon(2)
	puts h.removeWeapon(0)
	puts h
end
