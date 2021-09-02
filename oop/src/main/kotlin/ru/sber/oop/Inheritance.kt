package ru.sber.oop

open class Room(val name: String, val size: Int) {

    constructor(name: String): this(name, 100)

    val goblin: Monster = Goblin(
            "Green Goblin",
            "enemy of spiderman",
            1000,
            "smoke bomb")

    protected open val dangerLevel = 5

    fun description() = "Room: $name"

    open fun load() = goblin.getSalutation()

}

fun Monster.getSalutation() = "Hi, Peter"

//TODO: create class TownSquare here...
class TownSquare(): Room(name = "Town Square", size = 1000)
{
    final override fun load() = "TownSquare is loaded..."
    override val dangerLevel = super.dangerLevel - 3
}

fun main()
{
    val room: Room = Room("Park", 10000)
    val player: Player = Player("Pater", true, 650, "web")
    println(room.load())
    println(room.goblin.attack(player))
    println(player.attack(room.goblin))
}
