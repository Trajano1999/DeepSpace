# encoding: UTF-8

require_relative 'Damage'
require_relative 'SpecificDamageToUI'

module Deepspace
    class SpecificDamage < Damage
        def initialize (wl, s) # wl: Array<WeaponType>, s: int
            super(s)
            @weapons = wl
        end

        attr_reader :weapons

        def self.newCopy (d) # d: SpecificDamage
            new(Array.new(d.weapons), d.nShields) 
        end
        
        def newCopy # "contructor de copia" de instancia
        	self.class.newCopy(self)
        end

        def getUIversion
            SpecificDamageToUI.new(self)
        end

        def to_s
            getUIversion.to_s
        end

        def arrayContainsType (w, t) # arrayContainsType (w: Array<Weapon>, t: WeaponType) : int
            for i in 0 .. w.length-1
                if(w[i].type == t)
                    return i
                end
            end
            -1
        end

        def adjust (weapons, s) # adjust (w: Array<Weapon>, s: Array<ShieldBooster>) : Damage
            wCopy = @weapons.clone # Creamos copia para no modificar atributo
            adjustedWeapons = weapons.map do |w|
                wCopy.delete_at(wCopy.index(w.type) || wCopy.length)
            end
            adjustedWeapons.compact! # Elimina nils
            self.class.new(adjustedWeapons, adjustNShields(s))
        end

        def discardWeapon(w) # discardWeapon (w: Weapon) : void 
            @weapons.delete_if {|x| x == w.type}
        end

        def hasNoEffect
            super && (@weapons == nil || @weapons.length == 0)
        end

        # Especificación de acceso a métodos privados
        # private :arrayContainsType
    end
end

if $0 == __FILE__
    require_relative 'Weapon'
    require_relative 'WeaponType'
    require_relative 'ShieldBooster'

    arrayWT = Array.new(3){Deepspace::WeaponType::LASER}
    d = Deepspace::SpecificDamage.new(arrayWT,4)
    puts d
    d2 = Deepspace::SpecificDamage.newCopy(d)
    puts d2

    arrayW = Array.new(2){Deepspace::Weapon.new("hola1",Deepspace::WeaponType::LASER,1)}
    puts d.arrayContainsType(arrayW, Deepspace::WeaponType::LASER)

    arrayS = Array.new(3){Deepspace::ShieldBooster.new("hola2", 2.0, 1)}
    puts d.adjust(arrayW,arrayS)

    d.discardWeapon(Deepspace::Weapon.new("hola1",Deepspace::WeaponType::LASER,1))
    puts d

    puts d.hasNoEffect
end
