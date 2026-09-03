document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       ELEMENTOS
       ===================================================== */

    const searchInput =
        document.getElementById(
            "trainerSearch"
        );

    const clearButton =
        document.getElementById(
            "trainerSearchClear"
        );

    const trainerRows =
        document.querySelectorAll(
            "[data-trainer-row]"
        );

    const visibleCount =
        document.getElementById(
            "trainerVisibleCount"
        );

    const noResults =
        document.getElementById(
            "trainerNoSearchResults"
        );


    /* =====================================================
       NORMALIZAR
       ===================================================== */

    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    /* =====================================================
       FILTRAR
       ===================================================== */

    const filterTrainers = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        trainerRows.forEach(row => {

            const email =
                normalizeText(
                    row.dataset.trainerEmail
                );

            const dui =
                normalizeText(
                    row.dataset.trainerDui
                );

            const specialty =
                normalizeText(
                    row.dataset.trainerSpecialty
                );

            const phone =
                normalizeText(
                    row.dataset.trainerPhone
                );


            const matches =
                email.includes(query) ||
                dui.includes(query) ||
                specialty.includes(query) ||
                phone.includes(query);


            row.hidden =
                !matches;


            if (matches) {
                visible++;
            }

        });


        /* CONTADOR */

        if (visibleCount) {

            visibleCount.textContent =
                visible;

        }


        /* BOTÓN LIMPIAR */

        if (clearButton) {

            clearButton.classList.toggle(
                "visible",
                query.length > 0
            );

        }


        /* EMPTY STATE */

        if (noResults) {

            noResults.hidden =
                visible !== 0 ||
                query.length === 0;

        }

    };


    /* =====================================================
       EVENTOS
       ===================================================== */

    searchInput?.addEventListener(
        "input",
        filterTrainers
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterTrainers();

            searchInput.focus();

        }
    );

});