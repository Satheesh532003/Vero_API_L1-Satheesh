package com.vero.api;

/**
 * TransactionCandidateTest.java
 *
 * This is your file. Write your tests here.
 *
 * The existing test coverage in this project is incomplete. Your job is to
 * add meaningful tests that verify the behaviour of the code you have written
 * or fixed.
 *
 * What that means:
 *   - Tests should validate real behaviour, not just assert that methods exist.
 *   - Consider testing at the controller level (MockMvc) as well as the service level.
 *   - Think about edge cases: what happens with an empty transaction list?
 *     What happens when a month has no transactions?
 *   - Tests must compile and pass cleanly when you submit.
 *
 * What good looks like:
 *   A test that would catch a regression if someone changed the logic you wrote.
 *   Not a test that exists to satisfy a requirement.
 *
 * AI tools are permitted and expected. Document how you used them in DECISIONS.md,
 * including what the tool got wrong or what you changed.
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionCandidateTest {

    @Test
    void topSpendingCategoriesShouldReturnHighestCategory() {

        List<Transaction> transactions = List.of(
                Transaction.builder()
                        .category(Category.FOOD)
                        .amount(new BigDecimal("100"))
                        .transactionDate(LocalDate.now())
                        .accountId(1L)
                        .description("Food")
                        .build(),

                Transaction.builder()
                        .category(Category.UTILITIES)
                        .amount(new BigDecimal("300"))
                        .transactionDate(LocalDate.now())
                        .accountId(1L)
                        .description("Bill")
                        .build()
        );

        Map<Category, BigDecimal> result =
                BudgetCalculator.getTopSpendingCategories(transactions, 1);

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("300"), result.get(Category.UTILITIES));
    }
}
