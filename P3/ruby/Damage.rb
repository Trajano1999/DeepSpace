# encoding: UTF-8 

require_relative 'DamageToUI'

module Deepspace 
    class Damage
        def initialize (w, s, wl)
            @nShields = s # : int, siempre válido

            # Sólo será válido uno de los siguientes:
            @nWeapons = w # : int
            @weapons = wl # : Array<WeaponType>
        end

        attr_reader :nWeapons, :nShields, :weapons

        def self.newNumericWeapons (w, s) # w: int, s: int
        	new(w, s, nil)
        end
        
        def self.newSpecificWeapons (wl, s) # wl: Array<WeaponType>, s: int
        	new(-1, s, wl)
        end

        def self.newCopy (d)
        	if d.weapons != nil && d.nWeapons == -1
        		newSpecificWeapons(Array.new(d.weapons), d.nShields)
            elsif d.weapons == nil && d.nWeapons != -1
        		newNumericWeapons(d.nWeapons, d.nShields)
        	end
        end

        def getUIversion
            DamageToUI.new(self)
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
            nSh = [@nShields, s.length].min

            if @nWeapons == -1 # Hay lista especifica
                wCopy = @weapons.clone # Creamos copia para no modificar atributo
                adjustedWeapons = weapons.map do |w|
                    wCopy.delete_at(wCopy.index(w.type) || wCopy.length)
                end
                adjustedWeapons.compact! # Elimina nils
                self.class.newSpecificWeapons(adjustedWeapons, nSh)
            else
                self.class.newNumericWeapons([@nWeapons,weapons.length].min, nSh)
            end 
        end

        # Cosas horribles
=begin
        def adjust(w, s)
            nShields_copy = @nShields < s.length ? @nShields : s.length

            if @nWeapons == -1 && @weapons != nil
                weapons_copy = Array.new(@weapons)
                self.class.newSpecificWeapons(w.map {|w| weapons_copy.delete_at(weapons_copy.index(w.type) || weapons_copy.length) }, nShields_copy)
            elsif @nWeapons != -1 && @weapons == nil
                self.class.newNumericWeapons(@nWeapons < w.length ? @nWeapons: w.length, nShields_copy)
            end
        end
=end

        def discardWeapon(w) # discardWeapon (w: Weapon) : void
            if @weapons != nil && @nWeapons == -1
        		@weapons.delete_if {|x| x == w.type}
        	elsif @weapons == nil && @nWeapons != -1
        		@nWeapons = @nWeapons > 0 ? @nWeapons - 1 : 0
        	end
        end

        def discardShieldBooster
            if(@nShields > 0)
                @nShields -= 1
            end
        end

        def hasNoEffect
           # nShields == 0 && (nWeapons == 0 || weapons == nil || weapons.length == 0)
            numericNoEffect = (@nWeapons == 0 && @weapons == nil)
        	specificNoEffect = (@nWeapons == -1 && @weapons.length == 0)
        	@nShields == 0 && (specificNoEffect|| numericNoEffect)
        end

        # Especificación de acceso a métodos privados
        private_class_method :new
        private :arrayContainsType

    end
end

if $0 == __FILE__
    require_relative 'Weapon'
    require_relative 'WeaponType'
    require_relative 'ShieldBooster'

    d = Deepspace::Damage.newNumericWeapons(3,4)
    puts d.to_s

    d3 = Deepspace::Damage.newSpecificWeapons(Array.new(3), 4)
    puts d3.to_s

    d2 = Deepspace::Damage.newCopy(d)
    puts d2.to_s

    d4 = Deepspace::Damage.newCopy(d3)
    puts d4.to_s

    puts d.getUIversion.to_s

    el_vector = Array.new(2){Deepspace::Weapon.new("hola",Deepspace::WeaponType::LASER,2)}
    puts d.arrayContainsType(el_vector,Deepspace::WeaponType::LASER)

    puts

    p5 = Deepspace::Damage.newSpecificWeapons(Array.new(2){Deepspace::WeaponType::LASER},5)
    puts p5.to_s
    puts
    el_vector1 = Array.new(2) {Deepspace::Weapon.new("hola1",Deepspace::WeaponType::LASER,3)}
    el_vector2 = Array.new(2) {Deepspace::ShieldBooster.new("hola2",2,3)}
    puts p5.adjust(el_vector1, el_vector2).to_s

    puts
    w = Deepspace::Weapon.new(1,Deepspace::WeaponType::PLASMA,3)
    d.discardWeapon(w)
    puts d.to_s

    d.discardShieldBooster
    puts d.to_s
end
