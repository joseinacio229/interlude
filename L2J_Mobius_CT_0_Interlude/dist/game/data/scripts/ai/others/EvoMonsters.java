/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.others;

import java.util.HashMap;
import java.util.Map;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.model.actor.Attackable;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.network.serverpackets.ExShowScreenMessage;

import ai.AbstractNpcAI;

/**
 * Angel spawns...when one of the angels in the keys dies, the other angel will spawn.
 */
public class EvoMonsters extends AbstractNpcAI
{
	private static final Map<Integer, Integer> ANGELSPAWNS = new HashMap<>();
	static
	{
		ANGELSPAWNS.put(50001, 50002);
		ANGELSPAWNS.put(50002, 50003);
		ANGELSPAWNS.put(50003, 50004);
		
	}
	
	private EvoMonsters()
	{
		addKillId(ANGELSPAWNS.keySet());
	}
	
	@Override
	public String onKill(Npc npc, Player killer, boolean isSummon)
	{
		final Attackable newNpc = (Attackable) addSpawn(ANGELSPAWNS.get(npc.getId()), npc);
		newNpc.setRunning();
		
		// Adiciona uma mensagem de morte para o monstro que acabou de morrer
		if (npc.getId() == 50001)
		{
			newNpc.broadcastPacket(new ExShowScreenMessage(Config.MOB_LEVEL_1_TXT, Config.MOB_LEVEL_TIME_1));
		}
		if (npc.getId() == 50002)
		{
			newNpc.broadcastPacket(new ExShowScreenMessage(Config.MOB_LEVEL_2_TXT, Config.MOB_LEVEL_TIME_2));
		}
		if (npc.getId() == 50003)
		{
			newNpc.broadcastPacket(new ExShowScreenMessage(Config.MOB_LEVEL_3_TXT, Config.MOB_LEVEL_TIME_3));
		}
		if (npc.getId() == 50004)
		{
			newNpc.broadcastPacket(new ExShowScreenMessage(Config.MOB_LEVEL_4_TXT, Config.MOB_LEVEL_TIME_4));
		}
		// Aguarda 5 segundos para spawnar o próximo monstro
		startQuestTimer("spawn_next_monster", Config.TIME_SPAWN_MOB_LEVELUP, newNpc, killer);
		
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new EvoMonsters();
	}
}
