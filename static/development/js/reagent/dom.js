// Compiled by ClojureScript 1.10.238 {}
goog.provide('reagent.dom');
goog.require('cljs.core');
goog.require('react_dom');
goog.require('reagent.impl.util');
goog.require('reagent.impl.template');
goog.require('reagent.impl.batching');
goog.require('reagent.ratom');
goog.require('reagent.debug');
goog.require('reagent.interop');
reagent.dom.global$module$react_dom = goog.global.ReactDOM;
if(typeof reagent.dom.imported !== 'undefined'){
} else {
reagent.dom.imported = null;
}
if(typeof reagent.dom.roots !== 'undefined'){
} else {
reagent.dom.roots = cljs.core.atom.call(null,cljs.core.PersistentArrayMap.EMPTY);
}
reagent.dom.unmount_comp = (function reagent$dom$unmount_comp(container){
cljs.core.swap_BANG_.call(null,reagent.dom.roots,cljs.core.dissoc,container);

return reagent.dom.global$module$react_dom.unmountComponentAtNode.call(null,container);
});
reagent.dom.render_comp = (function reagent$dom$render_comp(comp,container,callback){
var _STAR_always_update_STAR_1796 = reagent.impl.util._STAR_always_update_STAR_;
reagent.impl.util._STAR_always_update_STAR_ = true;

try{return reagent.dom.global$module$react_dom.render.call(null,comp.call(null),container,((function (_STAR_always_update_STAR_1796){
return (function (){
var _STAR_always_update_STAR_1797 = reagent.impl.util._STAR_always_update_STAR_;
reagent.impl.util._STAR_always_update_STAR_ = false;

try{cljs.core.swap_BANG_.call(null,reagent.dom.roots,cljs.core.assoc,container,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [comp,container], null));

reagent.impl.batching.flush_after_render.call(null);

if(!((callback == null))){
return callback.call(null);
} else {
return null;
}
}finally {reagent.impl.util._STAR_always_update_STAR_ = _STAR_always_update_STAR_1797;
}});})(_STAR_always_update_STAR_1796))
);
}finally {reagent.impl.util._STAR_always_update_STAR_ = _STAR_always_update_STAR_1796;
}});
reagent.dom.re_render_component = (function reagent$dom$re_render_component(comp,container){
return reagent.dom.render_comp.call(null,comp,container,null);
});
/**
 * Render a Reagent component into the DOM. The first argument may be
 *   either a vector (using Reagent's Hiccup syntax), or a React element. The second argument should be a DOM node.
 * 
 *   Optionally takes a callback that is called when the component is in place.
 * 
 *   Returns the mounted component instance.
 */
reagent.dom.render = (function reagent$dom$render(var_args){
var G__1799 = arguments.length;
switch (G__1799) {
case 2:
return reagent.dom.render.cljs$core$IFn$_invoke$arity$2((arguments[(0)]),(arguments[(1)]));

break;
case 3:
return reagent.dom.render.cljs$core$IFn$_invoke$arity$3((arguments[(0)]),(arguments[(1)]),(arguments[(2)]));

break;
default:
throw (new Error(["Invalid arity: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(arguments.length)].join('')));

}
});

reagent.dom.render.cljs$core$IFn$_invoke$arity$2 = (function (comp,container){
return reagent.dom.render.call(null,comp,container,null);
});

reagent.dom.render.cljs$core$IFn$_invoke$arity$3 = (function (comp,container,callback){
reagent.ratom.flush_BANG_.call(null);

var f = (function (){
return reagent.impl.template.as_element.call(null,((cljs.core.fn_QMARK_.call(null,comp))?comp.call(null):comp));
});
return reagent.dom.render_comp.call(null,f,container,callback);
});

reagent.dom.render.cljs$lang$maxFixedArity = 3;

reagent.dom.unmount_component_at_node = (function reagent$dom$unmount_component_at_node(container){
return reagent.dom.unmount_comp.call(null,container);
});
/**
 * Returns the root DOM node of a mounted component.
 */
reagent.dom.dom_node = (function reagent$dom$dom_node(this$){
return reagent.dom.global$module$react_dom.findDOMNode.call(null,this$);
});
reagent.impl.template.find_dom_node = reagent.dom.dom_node;
/**
 * Force re-rendering of all mounted Reagent components. This is
 *   probably only useful in a development environment, when you want to
 *   update components in response to some dynamic changes to code.
 * 
 *   Note that force-update-all may not update root components. This
 *   happens if a component 'foo' is mounted with `(render [foo])` (since
 *   functions are passed by value, and not by reference, in
 *   ClojureScript). To get around this you'll have to introduce a layer
 *   of indirection, for example by using `(render [#'foo])` instead.
 */
reagent.dom.force_update_all = (function reagent$dom$force_update_all(){
reagent.ratom.flush_BANG_.call(null);

var seq__1801_1805 = cljs.core.seq.call(null,cljs.core.vals.call(null,cljs.core.deref.call(null,reagent.dom.roots)));
var chunk__1802_1806 = null;
var count__1803_1807 = (0);
var i__1804_1808 = (0);
while(true){
if((i__1804_1808 < count__1803_1807)){
var v_1809 = cljs.core._nth.call(null,chunk__1802_1806,i__1804_1808);
cljs.core.apply.call(null,reagent.dom.re_render_component,v_1809);


var G__1810 = seq__1801_1805;
var G__1811 = chunk__1802_1806;
var G__1812 = count__1803_1807;
var G__1813 = (i__1804_1808 + (1));
seq__1801_1805 = G__1810;
chunk__1802_1806 = G__1811;
count__1803_1807 = G__1812;
i__1804_1808 = G__1813;
continue;
} else {
var temp__5535__auto___1814 = cljs.core.seq.call(null,seq__1801_1805);
if(temp__5535__auto___1814){
var seq__1801_1815__$1 = temp__5535__auto___1814;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__1801_1815__$1)){
var c__4319__auto___1816 = cljs.core.chunk_first.call(null,seq__1801_1815__$1);
var G__1817 = cljs.core.chunk_rest.call(null,seq__1801_1815__$1);
var G__1818 = c__4319__auto___1816;
var G__1819 = cljs.core.count.call(null,c__4319__auto___1816);
var G__1820 = (0);
seq__1801_1805 = G__1817;
chunk__1802_1806 = G__1818;
count__1803_1807 = G__1819;
i__1804_1808 = G__1820;
continue;
} else {
var v_1821 = cljs.core.first.call(null,seq__1801_1815__$1);
cljs.core.apply.call(null,reagent.dom.re_render_component,v_1821);


var G__1822 = cljs.core.next.call(null,seq__1801_1815__$1);
var G__1823 = null;
var G__1824 = (0);
var G__1825 = (0);
seq__1801_1805 = G__1822;
chunk__1802_1806 = G__1823;
count__1803_1807 = G__1824;
i__1804_1808 = G__1825;
continue;
}
} else {
}
}
break;
}

return "Updated";
});

//# sourceMappingURL=dom.js.map
