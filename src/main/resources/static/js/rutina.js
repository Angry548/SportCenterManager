document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById("routineSearch");

    const clearButton =
        document.getElementById("routineSearchClear");

    const routineCards =
        document.querySelectorAll("[data-routine-card]");

    const resultCount =
        document.getElementById("routineVisibleCount");

    const noSearchResults =
        document.getElementById("routineNoSearchResults");


    const normalizeText = value =>
        String(value ?? "")
            .toLowerCase()
            .trim();


    const filterRoutines = () => {

        if (!searchInput) {
            return;
        }

        const query =
            normalizeText(searchInput.value);

        let visible = 0;


        routineCards.forEach(card => {

            const routineName =
                normalizeText(
                    card.dataset.routineName
                );

            const clientEmail =
                normalizeText(
                    card.dataset.clientEmail
                );

            const trainerEmail =
                normalizeText(
                    card.dataset.trainerEmail
                );

            const matches =
                routineName.includes(query) ||
                clientEmail.includes(query) ||
                trainerEmail.includes(query);


            card.hidden = !matches;

            if (matches) {
                visible++;
            }

        });


        if (resultCount) {
            resultCount.textContent = visible;
        }


        if (clearButton) {

            clearButton.classList.toggle(
                "visible",
                query.length > 0
            );

        }


        if (noSearchResults) {

            noSearchResults.hidden =
                visible !== 0 ||
                query.length === 0;

        }

    };


    searchInput?.addEventListener(
        "input",
        filterRoutines
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }

            searchInput.value = "";

            filterRoutines();

            searchInput.focus();

        }
    );


    /* =====================================================
       CONFIRMACIÓN QUITAR EJERCICIO
       ===================================================== */

    const removeExerciseLinks =
        document.querySelectorAll(
            "[data-remove-exercise]"
        );


    removeExerciseLinks.forEach(link => {

        link.addEventListener(
            "click",
            event => {

                event.preventDefault();

                const destination =
                    link.getAttribute("href");


                if (!destination) {
                    return;
                }


                if (typeof Swal === "undefined") {

                    const confirmed =
                        window.confirm(
                            "¿Deseas quitar este ejercicio de la rutina?"
                        );

                    if (confirmed) {
                        window.location.href = destination;
                    }

                    return;
                }


                Swal.fire({

                    title: "Quitar ejercicio",

                    text:
                        "El ejercicio dejará de formar parte de esta rutina.",

                    icon: "warning",

                    showCancelButton: true,

                    confirmButtonText:
                        "Sí, quitar",

                    cancelButtonText:
                        "Cancelar",

                    reverseButtons: true

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