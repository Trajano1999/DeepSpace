# encoding: UTF-8 

require_relative 'DamageToUI'

module Deepspace 
    class Damage
        def initialize (s)
            @nShields = s # : int
        end

        attr_reader :nShields

        def adjustNShields (s) # adjustNShields (s: Array<ShieldBooster>) : int
            if s != nil
            	[@nShields, s.length].min
           	end
        end

        def discardShieldBooster
            if(@nShields > 0)
                @nShields -= 1
            end
        end

        def hasNoEffect
        	@nShields == 0
        end

        # Especificación de acceso a métodos
        protected :adjustNShields
    end
end
