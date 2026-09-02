document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "membershipSearch"
        );

    const clearButton =
        document.getElementById(
            "membershipSearchClear"
        );

    const membershipRows =
        document.querySelectorAll(
            "[data-membership-row]"
        );

    const visibleCount =
        document.getElementById(
            "membershipVisibleCount"
        );

    const noResults =
        document.getElementById(
            "membershipNoSearchResults"
        );


    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    const filterMemberships = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        membershipRows.forEach(row => {

            const client =
                normalizeText(
                    row.dataset.membershipClient
                );

            const type =
                normalizeText(
                    row.dataset.membershipType
                );


            const matches =
                client.includes(query) ||
                type.includes(query);


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
        filterMemberships
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterMemberships();

            searchInput.focus();

        }
    );


    /* =====================================================
       FECHAS
       ===================================================== */

    const startInput =
        document.querySelector(
            "[data-membership-start]"
        );

    const endInput =
        document.querySelector(
            "[data-membership-end]"
        );

    const durationElement =
        document.getElementById(
            "membershipDuration"
        );

    const periodMessage =
        document.getElementById(
            "membershipPeriodMessage"
        );

    const periodPreview =
        document.querySelector(
            ".membership-period-preview"
        );


    const parseDate = value => {

        if (!value) {
            return null;
        }


        const parts =
            value.split("-");


        if (parts.length !== 3) {
            return null;
        }


        return new Date(
            Number(parts[0]),
            Number(parts[1]) - 1,
            Number(parts[2])
        );

    };


    const updatePeriod = () => {

        if (
            !startInput ||
            !endInput
        ) {
            return;
        }


        const start =
            parseDate(startInput.value);

        const end =
            parseDate(endInput.value);


        /*
         * Evita seleccionar una fecha final
         * anterior a la fecha de inicio.
         */
        if (startInput.value) {

            endInput.min =
                startInput.value;

        }


        if (
            !start ||
            !end
        ) {

            if (durationElement) {
                durationElement.textContent = "—";
            }


            if (periodMessage) {
                periodMessage.textContent =
                    "Selecciona ambas fechas.";
            }


            periodPreview?.classList.remove(
                "invalid"
            );

            return;

        }


        const milliseconds =
            end.getTime() -
            start.getTime();


        const days =
            Math.round(
                milliseconds /
                (1000 * 60 * 60 * 24)
            );


        if (days < 0) {

            if (durationElement) {
                durationElement.textContent =
                    "Periodo inválido";
            }


            if (periodMessage) {
                periodMessage.textContent =
                    "El vencimiento no puede ser anterior al inicio.";
            }


            periodPreview?.classList.add(
                "invalid"
            );

            return;

        }


        periodPreview?.classList.remove(
            "invalid"
        );


        if (durationElement) {

            durationElement.textContent =
                `${days} ${days === 1 ? "día" : "días"}`;

        }


        if (periodMessage) {

            periodMessage.textContent =
                "Periodo de membresía seleccionado.";

        }

    };


    startInput?.addEventListener(
        "change",
        updatePeriod
    );


    endInput?.addEventListener(
        "change",
        updatePeriod
    );


    updatePeriod();

});