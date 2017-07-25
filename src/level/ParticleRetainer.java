package level;

import java.awt.Graphics;
import java.util.ArrayList;

import particleEffects.Particle;

public class ParticleRetainer {

	private ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public ParticleRetainer(){}
		
	public void add(Particle p){
		particles.add(p);
	}
	
	public void clear(){
		ArrayList<Particle> liveParticles = new ArrayList<Particle>();
		for(Particle p: particles){
			if(p.getFade() != 0)
				liveParticles.add(p);
		}
		particles = liveParticles;
	}
	
	public void tick(){
		for(Particle p: particles)
			p.tick();
		clear();
	}
	
	public void draw(Graphics g){
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).draw(g);
		}
	}
}
