package tasks

import contributors.User

/*
TODO: Write aggregation code.

 In the initial list each user is present several times, once for each
 repository he or she contributed to.
 Merge duplications: each user should be present only once in the resulting list
 with the total value of contributions for all the repositories.
 Users should be sorted in a descending order by their contributions.

 The corresponding test can be found in test/tasks/AggregationKtTest.kt.
 You can use 'Navigate | Test' menu action (note the shortcut) to navigate to the test.
*/
fun List<User>.aggregate(): List<User> {

    val aggregatedUsers = mutableListOf<User>()

    for (user in this) {

        val existingUser = aggregatedUsers.find { it.login == user.login }

        if (existingUser != null) {

            val updatedUser = existingUser.copy(
                contributions = existingUser.contributions + user.contributions
            )

            val index = aggregatedUsers.indexOf(existingUser)
            aggregatedUsers[index] = updatedUser

        } else {
            aggregatedUsers.add(user)
        }
    }

    return aggregatedUsers.sortedByDescending { it.contributions }
}