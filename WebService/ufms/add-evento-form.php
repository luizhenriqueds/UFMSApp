<div class="mdl-card mdl-shadow--4dp mdl-cell--12-col">
    <div class="mdl-cell--12-col">
        <form class="col s12">
            <div class="row">
                <div class="input-field col s12">
                    <input id="evento-name" type="text" class="validate">
                    <label for="evento-name">Nome do Evento</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <textarea id="descricao-evento" class="materialize-textarea"></textarea>
                    <label for="descricao-evento">Descrição do Evento</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <label for="data-limite">Data Limite</label>
                    <input type="date" class="datepicker" id="data-limite">
                </div>
            </div>
            <div class="row">
                <div class="input-field col s6">
                    <select id="tipo-evento">
                        <option value="" disabled selected>Selecione uma opção</option>
                        <option value="1">Option 1</option>
                        <option value="2">Option 2</option>
                        <option value="3">Option 3</option>
                    </select>
                    <label for="tipo-evento">Tipo de Evento</label>
                </div>

                <div class="input-field col s6">
                    <select id="disciplina">
                        <option value="" disabled selected>Selecione uma opção</option>
                        <option value="1">Option 1</option>
                        <option value="2">Option 2</option>
                        <option value="3">Option 3</option>
                    </select>
                    <label for="disciplina">Disciplina</label>
                </div>

            </div>

            <div class="row">
                <div class="input-field col s12">
                    <div class="file-field input-field">
                        <div class="btn">
                            <span>File</span>
                            <input type="file" class="mdl-color--accent" multiple>
                        </div>
                        <div class="file-path-wrapper">
                            <input class="file-path validate" type="text" placeholder="Upload de um ou mais arquivos">
                        </div>
                    </div>
                </div>
            </div>

            <button
                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent form-buttons">
                Criar Evento
            </button>

        </form>
    </div>
</div>


<script>
    $(document).ready(function () {

        $('.datepicker').pickadate({
            format: 'mm/dd/yyyy',
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15 // Creates a dropdown of 15 years to control year
        });

        $('select').material_select();

    });

</script>


