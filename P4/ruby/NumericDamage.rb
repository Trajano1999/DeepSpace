# encoding: UTF-8

require_relative 'Damage'
require_relative 'NumericDamageToUI'

module Deepspace
    class NumericDamage < Damage
        def initialize (w, s) # w: int, s: int
            super(s)
            @nWeapons = w
        end

        attr_reader :nWeapons

        def self.newCopy (d) # d: NumericDamage
            new(d.nWeapons, d.nShields)
        end
        
        def newCopy # "contructor de copia" de instancia
        	self.class.newCopy(self)
        end

        def getUIversion
            NumericDamageToUI.new(self)
        end

        def to_s
            getUIversion.to_s
        end

        def adjust (weapons, s) # adjust (w: Array<Weapon>, s: Array<ShieldBooster>) : NumericDamage
            self.class.new([@nWeapons,weapons.length].min, adjustNShields(s))
        end

        def discardWeapon(w) # discardWeapon (w: Weapon) : void
            @nWeapons = @nWeapons > 0 ? @nWeapons - 1 : 0
        end

        def hasNoEffect
            super && @nWeapons == 0
        end
    end
end

if $0 == __FILE__
    require_relative 'Weapon'
    require_relative 'WeaponType'
    require_relative 'ShieldBooster'

    d = Deepspace::NumericDamage.new(3,4)
    puts d
    d2 = Deepspace::NumericDamage.newCopy(d)
    puts d2.to_s

    el_vector1 = Array.new(2) {Deepspace::Weapon.new("hola1",Deepspace::WeaponType::LASER,3)}
    el_vector2 = Array.new(2) {Deepspace::ShieldBooster.new("hola2",2,3)}
    puts d.adjust(el_vector1, el_vector2)

    w = Deepspace::Weapon.new(1,Deepspace::WeaponType::PLASMA,3)
    d.discardWeapon(w)
    puts d.to_s

    d.discardShieldBooster
    puts d.to_s

    puts d.hasNoEffect
end
