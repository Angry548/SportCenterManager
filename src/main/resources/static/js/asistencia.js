document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "attendanceSearch"
        );

    const clearButton =
        document.getElementById(
            "attendanceSearchClear"
        );

    const rows =
        document.querySelectorAll(
            "[data-attendance-row]"
        );

    const visibleCount =
        document.getElementById(
            "attendanceVisibleCount"
        );

    const noResults =
        document.getElementById(
            "attendanceNoSearchResults"
        );


    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    const filterAttendances = () => {

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
                    row.dataset.attendanceClient
                );

            const result =
                normalizeText(
                    row.dataset.attendanceResult
                );

            const date =
                normalizeText(
                    row.dataset.attendanceDate
                );


            const matches =
                client.includes(query) ||
                result.includes(query) ||
                date.includes(query);


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
        filterAttendances
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterAttendances();

            searchInput.focus();

        }
    );


    /* =====================================================
       VALIDAR ACCESO
       ===================================================== */

    const accessForms =
        document.querySelectorAll(
            ".attendance-access-form"
        );


    accessForms.forEach(form => {

        form.addEventListener(
            "submit",
            event => {

                event.preventDefault();


                if (typeof Swal === "undefined") {

                    const confirmed =
                        window.confirm(
                            "¿Deseas validar el acceso de este registro?"
                        );


                    if (confirmed) {
                        form.submit();
                    }

                    return;

                }


                Swal.fire({

                    title:
                        "Validar acceso",

                    text:
                        "Se comprobará si el cliente cumple las condiciones de acceso.",

                    icon:
                        "question",

                    showCancelButton:
                        true,

                    confirmButtonText:
                        "Sí, validar",

                    cancelButtonText:
                        "Cancelar",

                    reverseButtons:
                        true

                }).then(result => {

                    if (result.isConfirmed) {
                        form.submit();
                    }

                });

            }
        );

    });

});