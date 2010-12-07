<%-- select the visibility groups to which one should allow or deny the visibility. They groups should appear with the alias if it exists --%>
<fr:edit action="/contacts.do?method=setVisibilityGroups" id="groupsSelectorBean" name="groupsSelectorBean" schema="myorg.modules.contacts.ListPersistentGroups">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle5 thright thlight mtop05 ulnomargin"/>
			<fr:property name="columnClasses" value=",,tdclear tderror1"/>
		</fr:layout>
		<fr:destination name="cancel" path="/contacts.do?method=frontPage"/>
</fr:edit>