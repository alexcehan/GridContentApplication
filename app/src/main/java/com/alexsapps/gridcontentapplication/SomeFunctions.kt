package com.alexsapps.gridcontentapplication

import androidx.compose.runtime.snapshots.SnapshotStateList

//fun adjustListEnhanced2(numbers: SnapshotStateList<ListItem>) {
//    var i = 0
//    while (i < numbers.size) {
//        var count = 1 // Count of consecutive numbers
//
//        // Handle consecutive 1s
//        if (numbers[i].stateFullSize.value == 1) {
//            while (i + count < numbers.size && numbers[i + count].stateFullSize.value == 1) {
//                count++
//            }
//
//            // Check for even sequence of 1s followed by 0
//            if (count % 2 == 0 && i + count < numbers.size && numbers[i + count].stateFullSize.value == 0) {
//                numbers.removeAt(i + count) // Remove the 0 following the even sequence of 1s
//            } else if (count % 2 != 0) { // If odd count, add a 0
//                numbers.add(i + count, ListItem())
//                i++ // Increment to skip the added 0
//            }
//            i += count // Skip over the processed sequence of 1s
//        }
//        // Handle consecutive 0s
//        else if (numbers[i].stateFullSize.value == 0) {
//            while (i + count < numbers.size && numbers[i + count].stateFullSize.value == 0) {
//                count++
//            }
//            if (count > 2) { // If more than two 0s, remove extras
//                // Remove the extra 0s, keeping only two
//                for (j in 1..count - 2) {
//                    numbers.removeAt(i + 2) // Always remove at the third position of the sequence
//                }
//            }
//            i += Math.min(count, 2) // Skip the valid pair of 0s or however many were left
//        } else {
//            i++ // Move to the next element for any other number
//        }
//    }
//}