# encoding: UTF-8

module Deepspace
	class SuppliesPackage
		def initialize (ap, fu, sp) # SuppliesPackage(ap : float, fu : float, sp : float)
			@ammoPower = ap
			@fuelUnits = fu
			@shieldPower = sp
		end

		attr_reader :ammoPower
		attr_reader :fuelUnits
		attr_reader :shieldPower
		
		def self.newCopy (other) # Constructor de copia (es un método de clase)
			new(other.ammoPower, other.fuelUnits, other.shieldPower)
		end

		def to_s
			"ammoPower: #{@ammoPower}, fuelUnits: #{@fuelUnits}, shieldPower: #{@shieldPower}"
		end
	end
end

if $0 == __FILE__
	puts Deepspace::SuppliesPackage.new(1,2,3).to_s
end