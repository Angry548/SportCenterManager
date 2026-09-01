document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "exerciseSearch"
        );

    const clearButton =
        document.getElementById(
            "exerciseSearchClear"
        );

    const exerciseRows =
        document.querySelectorAll(
            "[data-exercise-row]"
        );

    const visibleCount =
        document.getElementById(
            "exerciseVisibleCount"
        );

    const noResults =
        document.getElementById(
            "exerciseNoSearchResults"
        );


    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    const filterExercises = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        exerciseRows.forEach(row => {

            const name =
                normalizeText(
                    row.dataset.exerciseName
                );

            const group =
                normalizeText(
                    row.dataset.exerciseGroup
                );

            const difficulty =
                normalizeText(
                    row.dataset.exerciseDifficulty
                );


            const matches =
                name.includes(query) ||
                group.includes(query) ||
                difficulty.includes(query);


            row.hidden = !matches;


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
        filterExercises
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterExercises();

            searchInput.focus();

        }
    );


    /* =====================================================
       PREVIEW DE IMAGEN
       ===================================================== */

    const imageUrlInput =
        document.getElementById(
            "imagenUrl"
        );

    const previewImage =
        document.getElementById(
            "exercisePreviewImage"
        );

    const placeholder =
        document.getElementById(
            "exerciseImagePlaceholder"
        );


    const showPlaceholder = () => {

        if (previewImage) {
            previewImage.hidden = true;
        }

        if (placeholder) {
            placeholder.hidden = false;
        }

    };


    const showImage = url => {

        if (!previewImage) {
            return;
        }


        previewImage.onload = () => {

            previewImage.hidden = false;

            if (placeholder) {
                placeholder.hidden = true;
            }

        };


        previewImage.onerror = () => {

            showPlaceholder();

        };


        previewImage.src = url;

    };


    const updatePreview = () => {

        if (!imageUrlInput) {
            return;
        }


        const url =
            imageUrlInput.value.trim();


        if (!url) {

            showPlaceholder();

            return;
        }


        showImage(url);

    };


    if (imageUrlInput) {

        updatePreview();


        imageUrlInput.addEventListener(
            "input",
            updatePreview
        );

    }

});