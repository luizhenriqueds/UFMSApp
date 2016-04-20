<button id="voltar-eventos" class="btn waves-effect waves-light back-button">Voltar</button>

<?php
require('UfmsDAO.php');
$tipoEventos = UfmsDAO::getTipoEventos();
$disciplinas = UfmsDAO::getDisciplinas();

?>

<div class="mdl-card mdl-shadow--4dp mdl-cell--12-col">
    <div class="mdl-cell--12-col">
        <form class="col s12" id="add-evento-form" method="post" enctype="multipart/form-data"
              action="action-add-evento.php">
            <div class="row">
                <div class="input-field col s12">
                    <input id="evento-name" name="evento-name" type="text" class="validate">
                    <label for="evento-name">Nome do Evento</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <textarea id="descricao-evento" name="descricao-evento" class="materialize-textarea"></textarea>
                    <label for="descricao-evento">Descrição do Evento</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <label for="data-limite">Data Limite</label>
                    <input type="date" class="datepicker" id="data-limite" name="data-limite">
                </div>
            </div>
            <div class="row">
                <div class="input-field col s6">
                    <select id="tipo-evento" name="tipo-evento">
                        <option value="" disabled selected>Selecione uma opção</option>
                        <?php
                        foreach ($tipoEventos as $row) {
                            echo '<option>';
                            print_r(utf8_encode($row['app_descricao_tipo_evento']));
                            echo '</option>';
                        }
                        ?>
                    </select>
                    <label for="tipo-evento">Tipo de Evento</label>
                </div>

                <div class="input-field col s6">
                    <select id="disciplina" name="disciplina">
                        <option value="" disabled selected>Selecione uma opção</option>
                        <?php
                        foreach ($disciplinas as $row) {
                            echo '<option>';
                            print_r(utf8_encode($row['app_titulo_disciplina']));
                            echo '</option>';
                        }
                        ?>
                    </select>
                    <label for="disciplina">Disciplina</label>
                </div>

            </div>

            <div class="row">
                <div class="input-field col s12">
                    <div class="file-field input-field">
                        <div class="btn" style="background-color:#448AFF;">
                            <span>File</span>
                            <input type="file" id="doc-anexo" name="doc-anexo" class="mdl-color--accent">
                        </div>
                        <div class="file-path-wrapper">
                            <input class="file-path validate" id="doc-anexo-upload" name="doc-anexo-upload" type="text" placeholder="Upload de um ou mais arquivos">
                        </div>
                    </div>
                </div>
            </div>

            <button type="submit"
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


        $("#voltar-eventos").click(function () {
            $("#eventos-content-container").load("list-eventos.php");
        });


    });

</script>


