document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "assignmentSearch"
        );

    const clearButton =
        document.getElementById(
            "assignmentSearchClear"
        );

    const assignmentRows =
        document.querySelectorAll(
            "[data-assignment-row]"
        );

    const visibleCount =
        document.getElementById(
            "assignmentVisibleCount"
        );

    const noResults =
        document.getElementById(
            "assignmentNoSearchResults"
        );


    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    const filterAssignments = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        assignmentRows.forEach(row => {

            const client =
                normalizeText(
                    row.dataset.assignmentClient
                );

            const trainer =
                normalizeText(
                    row.dataset.assignmentTrainer
                );

            const specialty =
                normalizeText(
                    row.dataset.assignmentSpecialty
                );


            const matches =
                client.includes(query) ||
                trainer.includes(query) ||
                specialty.includes(query);


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
        filterAssignments
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterAssignments();

            searchInput.focus();

        }
    );


    /* =====================================================
       FINALIZAR ASIGNACIÓN
       ===================================================== */

    const finalizeLinks =
        document.querySelectorAll(
            "[data-finalize-assignment]"
        );


    finalizeLinks.forEach(link => {

        link.addEventListener(
            "click",
            event => {

                event.preventDefault();


                const destination =
                    link.getAttribute("href");


                if (!destination) {
                    return;
                }


                /* FALLBACK */

                if (typeof Swal === "undefined") {

                    const confirmed =
                        window.confirm(
                            "¿Deseas finalizar esta asignación?"
                        );


                    if (confirmed) {

                        window.location.href =
                            destination;

                    }

                    return;

                }


                /* SWEETALERT */

                Swal.fire({

                    title:
                        "Finalizar asignación",

                    text:
                        "La relación dejará de aparecer como activa, pero su registro se conservará.",

                    icon:
                        "question",

                    showCancelButton:
                        true,

                    confirmButtonText:
                        "Sí, finalizar",

                    cancelButtonText:
                        "Cancelar",

                    reverseButtons:
                        true

                }).then(result => {

                    if (result.isConfirmed) {

                        window.location.href =
                            destination;

                    }

                });

            }
        );

    });

});