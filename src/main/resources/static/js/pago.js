document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "paymentSearch"
        );

    const clearButton =
        document.getElementById(
            "paymentSearchClear"
        );

    const rows =
        document.querySelectorAll(
            "[data-payment-row]"
        );

    const visibleCount =
        document.getElementById(
            "paymentVisibleCount"
        );

    const noResults =
        document.getElementById(
            "paymentNoSearchResults"
        );


    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    const filterPayments = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        rows.forEach(row => {

            const client =
                normalizeText(
                    row.dataset.paymentClient
                );

            const receipt =
                normalizeText(
                    row.dataset.paymentReceipt
                );

            const method =
                normalizeText(
                    row.dataset.paymentMethod
                );

            const membership =
                normalizeText(
                    row.dataset.paymentMembership
                );


            const matches =
                client.includes(query) ||
                receipt.includes(query) ||
                method.includes(query) ||
                membership.includes(query);


            row.hidden =
                !matches;


            if (matches) {
                visible++;
            }

        });


        if (visibleCount) {

            visibleCount.textContent =
                visible;

        }


        if (clearButton) {

            clearButton.classList.toggle(
                "visible",
                query.length > 0
            );

        }


        if (noResults) {

            noResults.hidden =
                visible !== 0 ||
                query.length === 0;

        }

    };


    searchInput?.addEventListener(
        "input",
        filterPayments
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterPayments();

            searchInput.focus();

        }
    );


    /* =====================================================
       CREATE - MEMBRESÍA
       ===================================================== */

    const membershipSelect =
        document.querySelector(
            "[data-payment-membership]"
        );

    const clientPreview =
        document.getElementById(
            "paymentClientPreview"
        );

    const typePreview =
        document.getElementById(
            "paymentTypePreview"
        );

    const planPricePreview =
        document.getElementById(
            "paymentPlanPricePreview"
        );

    const summaryClient =
        document.getElementById(
            "paymentSummaryClient"
        );

    const summaryMembership =
        document.getElementById(
            "paymentSummaryMembership"
        );


    const updateMembershipPreview = () => {

        if (!membershipSelect) {
            return;
        }


        const option =
            membershipSelect.options[
                membershipSelect.selectedIndex
                ];


        if (
            !option ||
            !option.value
        ) {

            if (clientPreview) {
                clientPreview.textContent =
                    "Selecciona una membresía";
            }

            if (typePreview) {
                typePreview.textContent = "—";
            }

            if (planPricePreview) {
                planPricePreview.textContent = "—";
            }

            if (summaryClient) {
                summaryClient.textContent = "—";
            }

            if (summaryMembership) {
                summaryMembership.textContent = "—";
            }

            return;

        }


        const client =
            option.dataset.client || "—";

        const type =
            option.dataset.type || "—";

        const price =
            option.dataset.price || "—";


        if (clientPreview) {
            clientPreview.textContent = client;
        }

        if (typePreview) {
            typePreview.textContent = type;
        }

        if (planPricePreview) {
            planPricePreview.textContent = price;
        }

        if (summaryClient) {
            summaryClient.textContent = client;
        }

        if (summaryMembership) {
            summaryMembership.textContent = type;
        }

    };


    membershipSelect?.addEventListener(
        "change",
        updateMembershipPreview
    );


    updateMembershipPreview();


    /* =====================================================
       CREATE - MÉTODO
       ===================================================== */

    const methodSelect =
        document.querySelector(
            "[data-payment-method]"
        );

    const summaryMethod =
        document.getElementById(
            "paymentSummaryMethod"
        );


    const updateMethodPreview = () => {

        if (
            !methodSelect ||
            !summaryMethod
        ) {
            return;
        }


        const option =
            methodSelect.options[
                methodSelect.selectedIndex
                ];


        if (
            !option ||
            !option.value
        ) {

            summaryMethod.textContent = "—";

            return;

        }


        summaryMethod.textContent =
            option.dataset.name ||
            option.textContent.trim();

    };


    methodSelect?.addEventListener(
        "change",
        updateMethodPreview
    );


    updateMethodPreview();


    /* =====================================================
       CREATE - MONTO
       ===================================================== */

    const amountInput =
        document.querySelector(
            "[data-payment-amount]"
        );

    const summaryAmount =
        document.getElementById(
            "paymentSummaryAmount"
        );


    const updateAmountPreview = () => {

        if (
            !amountInput ||
            !summaryAmount
        ) {
            return;
        }


        const value =
            parseFloat(
                amountInput.value
            );


        if (
            Number.isNaN(value) ||
            value <= 0
        ) {

            summaryAmount.textContent =
                "0.00";

            return;

        }


        summaryAmount.textContent =
            value.toFixed(2);

    };


    amountInput?.addEventListener(
        "input",
        updateAmountPreview
    );


    updateAmountPreview();

});