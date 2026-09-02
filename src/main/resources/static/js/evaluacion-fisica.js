document.addEventListener("DOMContentLoaded", () => {

    /* =====================================================
       BÚSQUEDA LOCAL
       ===================================================== */

    const searchInput =
        document.getElementById(
            "evaluationSearch"
        );

    const clearButton =
        document.getElementById(
            "evaluationSearchClear"
        );

    const evaluationRows =
        document.querySelectorAll(
            "[data-evaluation-row]"
        );

    const visibleCount =
        document.getElementById(
            "evaluationVisibleCount"
        );

    const noResults =
        document.getElementById(
            "evaluationNoSearchResults"
        );


    const normalizeText = value => {

        return String(value ?? "")
            .toLowerCase()
            .trim();

    };


    const filterEvaluations = () => {

        if (!searchInput) {
            return;
        }


        const query =
            normalizeText(
                searchInput.value
            );


        let visible = 0;


        evaluationRows.forEach(row => {

            const client =
                normalizeText(
                    row.dataset.evaluationClient
                );

            const trainer =
                normalizeText(
                    row.dataset.evaluationTrainer
                );

            const specialty =
                normalizeText(
                    row.dataset.evaluationSpecialty
                );


            const matches =
                client.includes(query) ||
                trainer.includes(query) ||
                specialty.includes(query);


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
        filterEvaluations
    );


    clearButton?.addEventListener(
        "click",
        () => {

            if (!searchInput) {
                return;
            }


            searchInput.value = "";

            filterEvaluations();

            searchInput.focus();

        }
    );


    /* =====================================================
       IMC PREVIEW
       ===================================================== */

    const weightInput =
        document.querySelector(
            "[data-evaluation-weight]"
        );

    const heightInput =
        document.querySelector(
            "[data-evaluation-height]"
        );

    const imcValue =
        document.getElementById(
            "evaluationImcValue"
        );


    const updateImcPreview = () => {

        if (
            !weightInput ||
            !heightInput ||
            !imcValue
        ) {
            return;
        }


        const weight =
            parseFloat(
                weightInput.value
            );

        const height =
            parseFloat(
                heightInput.value
            );


        if (
            Number.isNaN(weight) ||
            Number.isNaN(height) ||
            weight <= 0 ||
            height <= 0
        ) {

            imcValue.textContent = "—";

            return;

        }


        const imc =
            weight /
            (height * height);


        imcValue.textContent =
            imc.toFixed(2);

    };


    weightInput?.addEventListener(
        "input",
        updateImcPreview
    );


    heightInput?.addEventListener(
        "input",
        updateImcPreview
    );


    updateImcPreview();


    /* =====================================================
       FECHA MÁXIMA = HOY
       ===================================================== */

    const evaluationDate =
        document.querySelector(
            "[data-evaluation-date]"
        );


    if (evaluationDate) {

        const today =
            new Date();


        const year =
            today.getFullYear();


        const month =
            String(
                today.getMonth() + 1
            ).padStart(2, "0");


        const day =
            String(
                today.getDate()
            ).padStart(2, "0");


        evaluationDate.max =
            `${year}-${month}-${day}`;

    }


    /* =====================================================
       CONTADOR DE OBSERVACIONES
       ===================================================== */

    const observations =
        document.querySelector(
            "[data-evaluation-observations]"
        );

    const observationCount =
        document.getElementById(
            "evaluationObservationCount"
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

});