package fun.mousewich.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class ModEntityDamageSource extends EntityDamageSource {
	public ModEntityDamageSource(String name, Entity source) { super(name, source); }

	public static DamageSource sonicBoom(Entity attacker) {
		return new ModEntityDamageSource("sonic_boom", attacker).setBypassesArmor().setUsesMagic();//.setBypassesProtection();
	}
}
