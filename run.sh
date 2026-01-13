#!/bin/bash
/usr/lib/jvm/java-21-openjdk-amd64/bin/java \
  -XX:+ShowCodeDetailsInExceptionMessages \
  --module-path ./bin \
  -m Controle_Financeiro/controlefinanceiro.Codigo_Completo_Modificado.Main
