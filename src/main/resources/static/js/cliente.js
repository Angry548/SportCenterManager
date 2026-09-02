document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       ELEMENTOS
       ===================================================== */

    const searchInput =
        document.getElementById("clientSearch");

    const clearButton =
        document.getElementById("clientSearchClear");

    const clientRows =
        document.querySelectorAll(
            "[data-client-row]"
        );

    const visibleCount =
        document.getElementById(
            "clientVisibleCount"
        );

    const noResults =
        document.getElementById(
            "clientNoSearchResults"
        );


    /* =====================================================
       NORMALIZAR TEXTO
       ===================================================== */

    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    /* =====================================================
       FILTRAR
       ===================================================== */

    const filterClients = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        clientRows.forEach(row => {

            const email =
                normalizeText(
                    row.dataset.clientEmail
                );

            const dui =
                normalizeText(
                    row.dataset.clientDui
                );

            const phone =
                normalizeText(
                    row.dataset.clientPhone
                );


            const matches =
                email.includes(query) ||
                dui.includes(query) ||
                phone.includes(query);


            row.hidden =
                !matches;


            if (matches) {
                visible++;
            }

        });


        /* Actualizar contador */

        if (visibleCount) {

            visibleCount.textContent =
                visible;

        }


        /* Mostrar / ocultar limpiar */

        if (clearButton) {

            clearButton.classList.toggle(
                "visible",
                query.length > 0
            );

        }


        /* Mostrar empty state */

        if (noResults) {

            noResults.hidden =
                visible !== 0 ||
                query.length === 0;

        }

    };


    /* =====================================================
       EVENTS
       ===================================================== */

    searchInput?.addEventListener(
        "input",
        filterClients
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterClients();

            searchInput.focus();

        }
    );

});