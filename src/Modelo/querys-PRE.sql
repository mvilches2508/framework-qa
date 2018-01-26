RECEPCION DE ENCARGO EN EL CD  (Poblar base con informacion)



select first 20 b.encacodigobarra

from mv_orden_flete a, mv_encargo b

where a.odflcodigo=b.odflcodigo

  and a.eprocodigo in (6) --TRA

  and b.nomicodigoactual!=0

  and a.odflfechemicorta>='2016-01-01'

  and a.agencodigodestino= 1467

  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)







ENCARGOS PE PARA NOMINAR A REPARTO CUALQUIER TIPO DE PAGO (Poblar base con informacion)



select first 20 b.encacodigobarra

from mv_orden_flete a, mv_encargo b

where a.odflcodigo=b.odflcodigo

  and a.eprocodigo in (7) --NRE

  AND A.tentcodigo=2 --TODOS

  and b.nomicodigoactual!=0

  and a.odflfechemicorta>='2016-01-01'

  and a.agencodigodestino= 1467

  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)







ENCARGOS PE PARA NOMINAR A REPARTO TIPO DE PAGO POR PAGAR (Poblar base con informacion)



select first 20 b.encacodigobarra

from mv_orden_flete a, mv_encargo b

where a.odflcodigo=b.odflcodigo

  and a.eprocodigo in (7) --RE

  AND A.tentcodigo=2

  AND A.tpagcodigo=3 --POR PAGAR

  and b.nomicodigoactual!=0

  and a.odflfechemicorta>='2016-01-01'

  and a.agencodigodestino= 1467

  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)







ENCARGOS PE PARA ENTREGA EN OFICINA TIPO DE PAGO POR PAGAR  (



select first 20 b.encacodigobarra

from mv_orden_flete a, mv_encargo b

where a.odflcodigo=b.odflcodigo

  and a.eprocodigo in (7) --PE

  AND A.tentcodigo=1

  AND A.tpagcodigo=3 --POR PAGAR

  and a.odflfechemicorta>='2017-01-01'

  and a.agencodigodestino= 273

  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=273)









ENCARGOS PE PARA ENTREGA EN OFICINA TIPO DE PAGO CUALQUIERA



select first 20 b.encacodigobarra

from mv_orden_flete a, mv_encargo b

where a.odflcodigo=b.odflcodigo

  and a.eprocodigo in (7) --PE

  AND A.tentcodigo=1

 -- AND A.tpagcodigo=3 --POR PAGAR

  and a.odflfechemicorta>='2017-01-01'

  and a.agencodigodestino= 273

  and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=273)





SOLICITUDES DE REDESTINACION HECHAS EN CD



select sol.solicodigo, sol.soliagencodigodof,sol.soliodflcodigo from mv_solic_redestina sol

inner join mv_orden_flete fle on fle.odflcodigo = sol.soliodflcodigo

where sol.soliagencodigodof=1467 and 

fle.eprocodigo in (3,7)







SOLICITUDES DE DECOMISO



select * from mv_decomiso

where decousuario= "DLAR" and agencodigo=1467





FOLIO DE RENDICION



select rendcodigo, rendfolio from mv_rendicion

inner join ma_usuario on ma_usuario.usuacodigo=mv_rendicion.usuacodigocrea

where ma_usuario.usuausuario="MXVS"







CONSULTA PARA EMISION DE RECLAMOS



SELECT * FROM mv_orden_flete where odflcodigo between 44087001 and 44087250 









TIPOS DE ESTADO 



  select * from ma_estado_proceso



eprocodigo	eprodescripcion			epronombre	

2		EXTRAVIADO                    	EX        	

3		PENDIENTE DESPACHO            	PD        	

4		NOMINADO                      	NOM       	

5		DESPACHADO                    	DES       	

6		EN TRANSITO                   	TRA       	

7		PENDIENTE ENTREGA             	PE        	

8		NOMINADO REPARTO              	NRE       	

9		EN REPARTO                    	RE        	

10		PENDIENTE ENTREGA C. EXCEPCION	PEX       	

11		CERRADO CON EXCEPCION         	CEX       	

12		REMATADO                      	REZ       	

13		REDESTINADO                   	RED       	

14		DECOMISADO                    	DEC       	

15		ANULADO                       	ANU       	

16		ENTREGADO PARCIAL             	ENP       	

17		EXTRAVIADO PARCIAL            	EXP       	

18		P. DESPACHO PARCIAL           	PDP       	

19		NOMINADO PARCIAL              	NOP       	

20		DESPACHADO PARCIAL            	DEP       	

21		EN TRANSITO PARCIAL           	TRP       	

22		PENDIENTE ENTREGA PARCIAL     	PEP       	

23		NOMINADO REPARTO PARCIAL      	NRP       	

24		EN REPARTO PARCIAL            	REP       	

25		P. ENTREGA EXCEPCION PARCIAL  	PXP       	

26		CERRADO EXCEPCION PARCIAL     	CEP       

27		REZAGO PARCIAL                	RZP       

28		REDESTINADO PARCIAL           	RDP      

29		EN BODEGA CLIENTE             	EBC      

31		DEVOLUCION PDA                	DOL      

30		ENTREGADO PDA                 	EOL      

1		ENTREGADO                     	ENT      









TIPOS DE ENTREGA



  select * from ma_tipo_entrega



tentcodigo	tentnombre	tentdescripcion

1		AGENCIA   	AGENCIA                       

2		DOMICILIO 	DOMICILIO                     









TIPO DE PAGO



tpagcodigo	tpagnombre	tpagdescripcion

1		CONTADO   	Contado                       

2		CTA CTE   	Con cargo a Cuenta Corriente  

3		POR PAGAR 	Por Pagar                     





Obtener número de reclamo que está asociado a una OF:

 

select reclfolio

from mv_reclamo

where odflcodigo=909488455

 

Obtener números de nómina contenedora:

 

select a.nomicodigo, a.agencodigoorigen, a.nomifechacreacion, b.tnomiscontenedor

from mv_nomina a, ma_tipo_nomina b

where a.tnomcodigo=b.tnomcodigo

  and a.nomifechacreacion>='2017-01-01'

  and b.tnomiscontenedor=1

  and b.tnomversion>0

order by a.nomicodigo desc 


select * from ma_tipo_nomina
mv_nomina 




select * 
                    from mv_emision_finiquito as ef
                    inner join mv_reclamo as r on ef.reclcodigo=r.reclcodigo
                    order by ef.efinfechacrea desc


select first  1 b.odflcodigo
                    from mv_orden_flete a, mv_encargo b
                    where a.odflcodigo=b.odflcodigo
                      and a.eprocodigo in (7) 
                      and b.nomicodigoactual=0
                      and a.compcodigoentrega=0
                      and a.odflfechemicorta>='2017-01-01'
                      and a.agencodigodestino= 1467
                      and b.ubiccodigoactual in (select c.ubifcodigo from ma_ubicacion_fisic c where c.agencodigo=1467)
                      