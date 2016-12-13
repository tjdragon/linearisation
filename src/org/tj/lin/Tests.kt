package org.tj.lin

import org.junit.Test as test
import org.junit.Assert.*

class Test {
	@test fun diamond() {
		val graph = Graph()
		val cycleAlgo = CycleAlgo()
		val linAlgo = LinAlgo()
		
		val p1 = PricingNode("1")
		val p2 = PricingNode("2")
		val p3 = PricingNode("3")
		val p4 = PricingNode("4")
		p1.addChild(p2)
		p1.addChild(p3)
		p2.addChild(p4)
		p3.addChild(p4)
		graph.addNodes(p1, p2, p3, p4)
		println(graph)
		
		assertFalse(cycleAlgo.containsCycle(graph))
		val pl1 = linAlgo.linearise(graph)
		println("diamond " + pl1)
		//assertTrue(pl1.size == 1)
	}
	
	@test fun pl1() {
		val graph = Graph()
		val cycleAlgo = CycleAlgo()
		val linAlgo = LinAlgo()
		
		assertFalse(cycleAlgo.containsCycle(graph))
		val pl0 = linAlgo.linearise(graph)
		assertTrue(pl0.isEmpty())
		
		val p1 = PricingNode("1")
		graph.nodes.add(p1)
		assertFalse(cycleAlgo.containsCycle(graph))
		val pl1 = linAlgo.linearise(graph)
		assertTrue(pl1.size == 1)
		println("pl1 " + pl1)
		
		val p2 = PricingNode("2")
		graph.nodes.add(p2)
		assertFalse(cycleAlgo.containsCycle(graph))
		val pl2 = linAlgo.linearise(graph)
		assertTrue(pl2.size == 2)
		println("pl2 " + pl2)
		
		p1.addChild(p2)
		assertFalse(cycleAlgo.containsCycle(graph))
		val pl3 = linAlgo.linearise(graph)
		assertTrue(pl3.size == 1)
		println("pl3 " + pl3)
	}
	
	@test fun cycle1() {
		val graph = Graph()
		val linAlgo = CycleAlgo()

		val c0 = linAlgo.containsCycle(graph)
		assertFalse(c0)
		
		val p1 = PricingNode("1")
		graph.nodes.add(p1)
		val c1 = linAlgo.containsCycle(graph)
		assertFalse(c1)

		val p2 = PricingNode("2")
		graph.nodes.add(p2)
		p1.children.add(p2)
		p2.children.add(p2)
		val c2 = linAlgo.containsCycle(graph)
		assertTrue(c2)
	}
	
	@test fun cycle2() {
		val graph = Graph()
		val linAlgo = CycleAlgo()

		val c0 = linAlgo.containsCycle(graph)
		assertFalse(c0)
		
		val p1 = PricingNode("1")
		p1.children.add(p1)
		graph.nodes.add(p1)
		val c1 = linAlgo.containsCycle(graph)
		assertTrue(c1)
	}
}
