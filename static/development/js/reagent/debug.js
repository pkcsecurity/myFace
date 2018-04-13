// Compiled by ClojureScript 1.10.238 {}
goog.provide('reagent.debug');
goog.require('cljs.core');
reagent.debug.has_console = typeof console !== 'undefined';
reagent.debug.tracking = false;
if(typeof reagent.debug.warnings !== 'undefined'){
} else {
reagent.debug.warnings = cljs.core.atom.call(null,null);
}
if(typeof reagent.debug.track_console !== 'undefined'){
} else {
reagent.debug.track_console = (function (){var o = ({});
o.warn = ((function (o){
return (function() { 
var G__1409__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"warn","warn",-436710552)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__1409 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__1410__i = 0, G__1410__a = new Array(arguments.length -  0);
while (G__1410__i < G__1410__a.length) {G__1410__a[G__1410__i] = arguments[G__1410__i + 0]; ++G__1410__i;}
  args = new cljs.core.IndexedSeq(G__1410__a,0,null);
} 
return G__1409__delegate.call(this,args);};
G__1409.cljs$lang$maxFixedArity = 0;
G__1409.cljs$lang$applyTo = (function (arglist__1411){
var args = cljs.core.seq(arglist__1411);
return G__1409__delegate(args);
});
G__1409.cljs$core$IFn$_invoke$arity$variadic = G__1409__delegate;
return G__1409;
})()
;})(o))
;

o.error = ((function (o){
return (function() { 
var G__1412__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"error","error",-978969032)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__1412 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__1413__i = 0, G__1413__a = new Array(arguments.length -  0);
while (G__1413__i < G__1413__a.length) {G__1413__a[G__1413__i] = arguments[G__1413__i + 0]; ++G__1413__i;}
  args = new cljs.core.IndexedSeq(G__1413__a,0,null);
} 
return G__1412__delegate.call(this,args);};
G__1412.cljs$lang$maxFixedArity = 0;
G__1412.cljs$lang$applyTo = (function (arglist__1414){
var args = cljs.core.seq(arglist__1414);
return G__1412__delegate(args);
});
G__1412.cljs$core$IFn$_invoke$arity$variadic = G__1412__delegate;
return G__1412;
})()
;})(o))
;

return o;
})();
}
reagent.debug.track_warnings = (function reagent$debug$track_warnings(f){
reagent.debug.tracking = true;

cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

f.call(null);

var warns = cljs.core.deref.call(null,reagent.debug.warnings);
cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

reagent.debug.tracking = false;

return warns;
});

//# sourceMappingURL=debug.js.map
