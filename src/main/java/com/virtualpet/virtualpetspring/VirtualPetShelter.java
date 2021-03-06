package com.virtualpet.virtualpetspring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VirtualPetShelter {

	private Map<String, Pet> pets = new HashMap<>();
	private Map<String, Cage> cages = new HashMap<>();
	private LitterBox litterBox;

	public Pet getPet(String name) {
		return pets.get(name);
	}

	public Collection<Pet> getPets() {
		return pets.values();
	}

	public Cage getCage(String name) {
		return new Cage();
	}

	public Collection<Cage> getCages() {
		return cages.values();
	}

	public int numberOfPets() {
		return getPets().size();
	}

	public int numberOfCages() {
		return cages.size();
	}

	public int getDirtyness() {
		return litterBox.getDirtyness();
	}

	public Set<String> getNames() {
		return pets.keySet();
	}

	public LitterBox getLitterBox() {
		return litterBox;
	}

	public Collection<String> getNamesAndDescriptions() {
		Collection<String> result = new ArrayList<>();
		for (Pet pet : getPets()) {
			result.add(pet.getName() + " | " + pet.getDescription());
		}
		return result;
	}

	public VirtualPetShelter(LitterBox litterBox) {
		this.litterBox = litterBox;
	}

	public void add(Pet pet) {
		String petName = pet.getName();
		if (!pets.containsKey(petName)) {
			pets.put(petName, pet);
			if (pet instanceof OrganicDog) {
				cages.put(petName, ((OrganicDog) pet).getCage());
			}
		}
	}

	public void walk() {
		getPets().forEach(pet -> {
			if (pet instanceof Walkable) {
				((Walkable) pet).walk();
			}
		});
	}

	public void adoptPet(String name) {
		pets.remove(name);
	}

	public void oilAllRobots() {
		getPets().forEach(pet -> {
			if (pet instanceof Robotic) {
				((Robotic) pet).oil();
			}
		});
	}

	public void feed() {
		getPets().forEach(pet -> {
			if (pet instanceof Organic) {
				((Organic) pet).feed();
			}
		});
	}

	public void tick() {
		getPets().forEach(pet -> pet.tick());
		getPets().removeIf(pet -> !pet.isAlive());
	}

	public void emptyLitterBox() {
		litterBox.clean();
	}

	public void cleanCages() {
		cages.forEach((petName, cage) -> cage.clean());
	}

	@Override
	public String toString() {
		String tab = "\t|";
		String result = "Name\t|Type\t|Health\t|Boredom|Hunger\t|Thirst\t|Waste\t|Dryness|Dirtyness\n";
		for (Pet pet : getPets()) {
			String type = "";
			if (pet instanceof OrganicDog) {
				type = "OrgDog";
			} else if (pet instanceof OrganicCat) {
				type = "OrgCat";
			} else if (pet instanceof RoboticDog) {
				type = "RobDog";
			} else {
				type = "RobCat";
			}
			if (pet instanceof Organic) {
				Organic organic = (Organic) pet;
				result += organic.getName() + tab + type + tab + organic.getHealth() + tab + organic.getBoredom() + tab
						+ organic.getHunger() + tab + organic.getThirst() + tab + organic.getWaste() + tab + "N/A" + tab
						+ organic.getDirtyness() + "\n";
			} else {
				Robotic robot = (Robotic) pet;
				result += robot.getName() + tab + type + tab + robot.getHealth() + tab + robot.getBoredom() + tab
						+ "N/A" + tab + "N/A" + tab + "N/A" + tab + robot.getDryness() + tab + "N/A" + "\n";
			}
		}
		return result;
	}

	public void water() {
		getPets().forEach(pet -> {
			if (pet instanceof Organic) {
				((Organic) pet).water();
			}
		});
	}

	public void play(String name) {
		if (pets.containsKey(name)) {
			getPet(name).play();
		}
	}

	public int numberOfRoboticPets() {
		int result = 0;
		for (Pet pet : getPets()) {
			if (pet instanceof Robotic) {
				result++;
			}
		}
		return result;
	}

	public int numberOfWalkablePets() {
		int result = 0;
		for (Pet pet : getPets()) {
			if (pet instanceof Walkable) {
				result++;
			}
		}
		return result;
	}

	public int numberOfOrganicPets() {
		int result = 0;
		for (Pet pet : getPets()) {
			if (pet instanceof Organic) {
				result++;
			}
		}
		return result;
	}

}
