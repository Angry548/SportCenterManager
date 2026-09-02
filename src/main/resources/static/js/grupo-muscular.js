document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "muscleSearch"
        );

    const clearButton =
        document.getElementById(
            "muscleSearchClear"
        );

    const muscleRows =
        document.querySelectorAll(
            "[data-muscle-row]"
        );

    const visibleCount =
        document.getElementById(
            "muscleVisibleCount"
        );

    const noResults =
        document.getElementById(
            "muscleNoSearchResults"
        );


    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    const filterMuscles = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        muscleRows.forEach(row => {

            const name =
                normalizeText(
                    row.dataset.muscleName
                );


            const matches =
                name.includes(query);


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
        filterMuscles
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterMuscles();

            searchInput.focus();

        }
    );


    /* =====================================================
       FORMULARIO
       ===================================================== */

    const nameInput =
        document.querySelector(
            "[data-muscle-name-input]"
        );

    const characterCount =
        document.getElementById(
            "muscleCharacterCount"
        );

    const namePreview =
        document.getElementById(
            "muscleNamePreview"
        );


    const updateMusclePreview = () => {

        if (!nameInput) {
            return;
        }


        const value =
            nameInput.value.trim();


        if (characterCount) {

            characterCount.textContent =
                nameInput.value.length;

        }


        if (namePreview) {

            namePreview.textContent =
                value || "Nombre del grupo";

        }

    };


    nameInput?.addEventListener(
        "input",
        updateMusclePreview
    );


    updateMusclePreview();

});