# encoding: UTF-8

require_relative 'ShieldToUI'

module Deepspace
	class ShieldBooster
		def initialize (n, b, u) # ShieldBooster(n : string, b : float, u : int)
			@name = n
			@boost = b
			@uses = u
		end
		
		attr_reader :name
		attr_reader :boost
		attr_reader :uses
		
		def self.newCopy (other)
			new(other.name, other.boost, other.uses)
		end
		
		def getUIversion
			ShieldToUI.new(self)
		end

		def to_s
			getUIversion.to_s 
		end
		
		def useIt
			if @uses > 0
				@uses -= 1
				@boost
			else
				1.0
			end
		end
	end
end

if $0 == __FILE__
	puts Deepspace::ShieldBooster.new(1, 2, 3)
end
