<table class="mdl-data-table" id="eventos-table" cellspacing="0"
       width="100%">
    <thead>
    <tr>
        <th class="mdl-data-table__cell--non-numeric">Nome do Evento</th>
        <th>Descrição</th>
        <th>Data Criado</th>
        <!--<th>Data Limite</th>-->
        <th>Tipo de Evento</th>
        <th>Disciplina</th>
    </tr>

    </thead>
    <tbody>
    <?php

    require("UfmsDAO.php");
    $eventos = UfmsDAO::getAllEventos();


    foreach ($eventos as $row) {
        echo '
          <tr>
           <td class="mdl-data-table__cell--non-numeric">' . $row['app_nome_evento'] . '</td>
           <td>' . utf8_encode(substr($row['app_descricao_evento'], 0, 20)) . '...</td>
           <td>' . date("d/m/Y", strtotime($row['app_evento_timestamp'])) . '</td>
           <td>' . utf8_encode($row['app_descricao_tipo_evento']) . '</td>
           <td>' . $row['app_titulo_disciplina'] . '</td>
         </tr>';
    }


    ?>

    </tbody>
</table>

<button
    class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored mdl-shadow--4dp"
    id="add">
    <i class="material-icons">add</i>
</button>

<script>
    $(document).ready(function () {
        $('#eventos-table').DataTable({
            columnDefs: [
                {
                    targets: [0, 1, 2],
                    className: 'mdl-data-table__cell--non-numeric'
                }
            ]
        });


        $("#add").click(function () {
            $("#eventos-content-container").load("add-evento-form.php");
        });

        $('select').material_select();

    });
</script>