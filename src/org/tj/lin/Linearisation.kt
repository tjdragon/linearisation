package org.tj.lin

data class PricingNode(val id: String) {
	val metadata: MutableMap<String, Any> = mutableMapOf()
	
	val parents: MutableList<PricingNode> = mutableListOf()
	val children: MutableList<PricingNode> = mutableListOf()
	
	var calcIndex = 0
	
	fun isPricingNode() = parents.isEmpty()
	
	fun addChild(child: PricingNode) {
		children.add(child)
		child.parents.add(this)
	}
	
	override fun toString(): String = id + "{" + calcIndex + "} -> " + children.map { c -> c.id }
}

class Graph {
	val nodes: MutableList<PricingNode> = mutableListOf()
	
	fun addNode(n: PricingNode) {
		nodes.add(n)
	}
	
	fun addNodes(vararg nodes: PricingNode) {
		nodes.forEach { p -> addNode(p) }
	}
	
	override fun toString(): String = "G " + nodes.toString()
}

class CycleAlgo {
	val colour = "colour"
	enum class Marker {white, black, grey}
	
	fun containsCycle(graph: Graph): Boolean {
		graph.nodes.forEach { n -> n.metadata.put(colour, Marker.white) }
		
		graph.nodes.forEach { n ->
			if (Marker.white == n.metadata.get(colour)) {
				if (visit(n)) return true;
			}
		}
		
		return false;
	}
	
	private fun visit(node: PricingNode): Boolean {
		node.metadata.put(colour, Marker.grey)
		
		node.children.forEach { c ->
			val marker = c.metadata.get(colour)
			if (Marker.grey == marker) {
				return true
			} else if (Marker.white == marker) {
				if (visit(c)) {
					return true
				}
			}
		}
		
		node.metadata.put(colour, Marker.black)
		
		return false;
	}
}

class LinAlgo {
	val calcIndex = "calc-index"
	
	fun linearise(graph: Graph):MutableList<MutableList<PricingNode>> {
		graph.nodes.forEach { n -> n.metadata.put(calcIndex, 0) }
		val pricingLists: MutableList<MutableList<PricingNode>> = mutableListOf()
		
		graph.nodes.forEach { n ->
			if (n.isPricingNode()) {
				markDownFrom(n)
				val nodes: MutableList<PricingNode> = mutableListOf()
				insert(n, nodes)
				var ci = 0
				nodes.forEach { n ->
					n.calcIndex = ci++
				}
				pricingLists.add(nodes)
			}
		}
		
		return pricingLists
	}
	
	private fun markDownFrom(node: PricingNode) {
		val index = node.metadata.get(calcIndex) as Int + 1
		node.metadata.put(calcIndex, index)
		node.children.forEach { c -> markDownFrom(c) }
	}
	
	private fun insert(node: PricingNode, list: MutableList<PricingNode>) {
		val index = node.metadata.get(calcIndex) as Int - 1
		node.metadata.put(calcIndex, index)
		if (index <= 0) {
			list.add(node)
			node.metadata.put(calcIndex, 0)
		}
		node.children.forEach { c -> insert(c, list) }
	}
}




