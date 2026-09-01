document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "routineExerciseSearch"
        );

    const clearButton =
        document.getElementById(
            "routineExerciseSearchClear"
        );

    const rows =
        document.querySelectorAll(
            "[data-routine-exercise-row]"
        );

    const visibleCount =
        document.getElementById(
            "routineExerciseVisibleCount"
        );

    const noResults =
        document.getElementById(
            "routineExerciseNoSearchResults"
        );


    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    const filterRows = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        rows.forEach(row => {

            const routineId =
                normalizeText(
                    row.dataset.routineId
                );

            const exercise =
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
                exercise.includes(query) ||
                group.includes(query) ||
                difficulty.includes(query) ||
                routineId.includes(query);


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
        filterRows
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterRows();

            searchInput.focus();

        }
    );


    /* =====================================================
       PREVIEW DE CONFIGURACIÓN
       ===================================================== */

    const seriesInput =
        document.querySelector(
            "[data-routine-series]"
        );

    const repetitionsInput =
        document.querySelector(
            "[data-routine-repetitions]"
        );

    const restInput =
        document.querySelector(
            "[data-routine-rest]"
        );

    const totalRepetitions =
        document.getElementById(
            "routineTotalRepetitions"
        );

    const restPreview =
        document.getElementById(
            "routineRestPreview"
        );


    const formatRest = seconds => {

        if (
            Number.isNaN(seconds) ||
            seconds < 0
        ) {
            return "—";
        }


        if (seconds < 60) {
            return `${seconds} s`;
        }


        const minutes =
            Math.floor(seconds / 60);

        const remaining =
            seconds % 60;


        if (remaining === 0) {

            return `${minutes} min`;

        }


        return `${minutes} min ${remaining} s`;

    };


    const updateWorkoutPreview = () => {

        const series =
            parseInt(
                seriesInput?.value ?? "",
                10
            );

        const repetitions =
            parseInt(
                repetitionsInput?.value ?? "",
                10
            );

        const rest =
            parseInt(
                restInput?.value ?? "",
                10
            );


        if (totalRepetitions) {

            if (
                Number.isNaN(series) ||
                Number.isNaN(repetitions) ||
                series < 1 ||
                repetitions < 1
            ) {

                totalRepetitions.textContent =
                    "—";

            } else {

                totalRepetitions.textContent =
                    series * repetitions;

            }

        }


        if (restPreview) {

            restPreview.textContent =
                formatRest(rest);

        }

    };


    seriesInput?.addEventListener(
        "input",
        updateWorkoutPreview
    );


    repetitionsInput?.addEventListener(
        "input",
        updateWorkoutPreview
    );


    restInput?.addEventListener(
        "input",
        updateWorkoutPreview
    );


    updateWorkoutPreview();


    /* =====================================================
       CONTADOR DE OBSERVACIONES
       ===================================================== */

    const observations =
        document.querySelector(
            "[data-routine-observations]"
        );

    const observationCount =
        document.getElementById(
            "routineObservationCount"
        );


    const updateObservationCount = () => {

        if (
            !observations ||
            !observationCount
        ) {
            return;
        }


        observationCount.textContent =
            observations.value.length;

    };


    observations?.addEventListener(
        "input",
        updateObservationCount
    );


    updateObservationCount();


    /* =====================================================
       FALLBACK DE IMÁGENES
       ===================================================== */

    const exerciseImages =
        document.querySelectorAll(
            "[data-routine-exercise-image], [data-routine-detail-image]"
        );


    exerciseImages.forEach(image => {

        image.addEventListener(
            "error",
            () => {

                image.style.display =
                    "none";

            }
        );

    });

});