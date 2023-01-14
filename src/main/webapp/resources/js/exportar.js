function exportChart() {
    $('#output').empty().append(PF('chart').exportAsImage());
    PF('dlg').show();
}