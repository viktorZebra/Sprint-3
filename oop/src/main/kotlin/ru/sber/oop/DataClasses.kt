package ru.sber.oop

/*
  Свойства, которые определяются вне первичного конструктора, не используются в функциях toString, equals и hashCode
  Для правильной работы функции есть два решения:
  1. внести свойство в конструтор;
  2. переопределить функцию equals();
  Второй вариант больше подходит для решения.
 */

data class User(val name: String, val age: Long) {
    lateinit var city: String

    override fun equals(other: Any?): Boolean
    {
        return (other is User) &&
                this::city.isInitialized &&
                other::city.isInitialized &&
                this.city == other.city &&
                this.age == other.age &&
                this.name == other.name
    }
}

fun main() {
    val user1 = User("Alex", 13)
    val user2 = user1.copy(name = "Artem")
    user1.city = "Omsk"
    val user3 = user1.copy()
    user3.city = "Tomsk"

    print(user3.equals(user1))
}