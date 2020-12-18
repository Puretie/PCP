package api;


import ninja.bytecode.shuriken.bukkit.world.Area;
import ninja.bytecode.shuriken.bukkit.world.VectorMath;
import ninja.bytecode.shuriken.collections.KList;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class Impull
{
	private double radius;
	private double forceMax;
	private double forceMin;
	private KList<Entity> ignore;
	private double damageMin;
	private double damageMax;

	public Impull(double radius)
	{
		ignore = new KList<Entity>();
		this.radius = radius;
		this.forceMax = 1;
		this.forceMin = 0;
		this.damageMax = 1;
		this.damageMin = 0;
	}

	public Impull radius(double radius)
	{
		this.radius = radius;
		return this;
	}

	public Impull force(double force)
	{
		this.forceMax = force;
		return this;
	}

	public Impull force(double forceMax, double forceMin)
	{
		this.forceMax = forceMax;
		this.forceMin = forceMin;
		return this;
	}

	public Impull damage(double damage)
	{
		this.damageMax = damage;
		return this;
	}

	public Impull damage(double damageMax, double damageMin)
	{
		this.damageMax = damageMax;
		this.damageMin = damageMin;
		return this;
	}

	public void punch(Location at)
	{
		Area a = new Area(at, radius);

		for(Entity i : a.getNearbyEntities())
		{
			if(ignore.contains(i))
			{
				continue;
			}

			Vector force = VectorMath.reverse(VectorMath.direction(at, i.getLocation()));
			double damage = 0;
			double distance = i.getLocation().distance(at);

			if(forceMin < forceMax)
			{
				force.clone().multiply(((1D - (distance / radius)) * (forceMax - forceMin)) + forceMin);
			}

			if(damageMin < damageMax)
			{
				damage = ((1D - (distance / radius)) * (damageMax - damageMin)) + damageMin;
			}

			try
			{
				if(i instanceof LivingEntity && damage > 0)
				{
					((LivingEntity) i).damage(damage);
				}

				i.setVelocity(i.getVelocity().add(force));
			}

			catch(Exception e)
			{

			}
		}
	}

	public Impull ignore(Entity player)
	{
		ignore.add(player);
		return this;
	}
}
