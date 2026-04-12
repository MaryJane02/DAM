package dam.virtual_library

abstract class Book(
    val title: String,
    val author: String,
    private val year: Int,
    availableCopies: Int
) {
    var availableCopies: Int = availableCopies
        set(value) {
            if (value < 0) {
                return
            }
            field = value
            if (value == 0) {
                println("Warning: Book is now out of stock!")
            }
        }

    val publicationYear: String
        get() = when {
            year < 1980 -> "Classic"
            year in 1980..2010 -> "Modern"
            else -> "Contemporary"
        }

    abstract fun getStorageInfo(): String

    override fun toString(): String {
        val copyText = if (availableCopies == 1) "copy" else "copies"
        return "Title: $title, Author: $author, Era: $publicationYear, Available: $availableCopies $copyText"
    }
}

class DigitalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    val fileSize: Double,
    val format: String
) : Book(title, author, publicationYear, availableCopies) {

    override fun getStorageInfo(): String {
        return "Stored digitally: ${"%.1f".format(fileSize)} MB, Format: $format"
    }
}

class PhysicalBook(
    title: String,
    author: String,
    publicationYear: Int,
    availableCopies: Int,
    val weight: Int,
    val hasHardcover: Boolean = true
) : Book(title, author, publicationYear, availableCopies) {

    override fun getStorageInfo(): String {
        val hardcoverText = if (hasHardcover) "Yes" else "No"
        return "Physical book: ${weight}g, Hardcover: $hardcoverText"
    }
}

class Library(val name: String) {

    private val books = mutableListOf<Book>()

    fun addBook(book: Book) {
        books.add(book)
        totalBooksAdded++
    }

    fun borrowBook(title: String) {
        val book = books.find { it.title.equals(title, ignoreCase = true) }

        if (book == null) {
            println("Sorry, book '$title' not found.")
            return
        }

        if (book.availableCopies > 0) {
            val newCopies = book.availableCopies - 1
            println("Successfully borrowed '${book.title}'. Copies remaining: $newCopies")
            book.availableCopies = newCopies
        } else {
            println("Sorry, no copies of '${book.title}' are currently available.")
        }
    }

    fun returnBook(title: String) {
        val book = books.find { it.title.equals(title, ignoreCase = true) }

        if (book == null) {
            println("Book '$title' not found.")
            return
        }

        book.availableCopies += 1
        println("Book '${book.title}' returned successfully. Copies available: ${book.availableCopies}")
    }

    fun showBooks() {
        println("--- Library Catalog ---")
        books.forEach { book ->
            println(book)
            println("Storage: ${book.getStorageInfo()}")
        }
    }

    fun searchByAuthor(author: String) {
        val results = books.filter { it.author.equals(author, ignoreCase = true) }

        if (results.isEmpty()) {
            println("No books found by $author.")
            return
        }

        println("Books by $author:")
        results.forEach { book ->
            val copyText = if (book.availableCopies == 1) "copy available" else "copies available"
            println("- ${book.title} (${book.publicationYear}, ${book.availableCopies} $copyText)")
        }
    }

    companion object {
        private var totalBooksAdded = 0

        fun getTotalBooksCreated(): Int {
            return totalBooksAdded
        }
    }
}

data class LibraryMember(
    val name: String,
    val membershipId: String,
    val borrowedBooks: List<String>
)

fun main() {
    val library = Library("Central Library")

    val digitalBook = DigitalBook(
        "Kotlin in Action",
        "Dmitry Jemerov",
        2017,
        5,
        4.5,
        "PDF"
    )

    val physicalBook = PhysicalBook(
        "Clean Code",
        "Robert C. Martin",
        2008,
        3,
        650,
        true
    )

    val classicBook = PhysicalBook(
        "1984",
        "George Orwell",
        1949,
        2,
        400,
        false
    )

    library.addBook(digitalBook)
    library.addBook(physicalBook)
    library.addBook(classicBook)

    library.showBooks()

    println("\n--- Borrowing Books ---")
    library.borrowBook("Clean Code")
    library.borrowBook("1984")
    library.borrowBook("1984")
    library.borrowBook("1984")

    println("\n--- Returning Books ---")
    library.returnBook("1984")

    println("\n--- Search by Author ---")
    library.searchByAuthor("George Orwell")
}