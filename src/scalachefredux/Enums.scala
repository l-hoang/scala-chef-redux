package scalachefredux


/* Chef operations */
abstract sealed class ChefOp

case object E_NONE extends ChefOp
case object E_TAKE extends ChefOp
case object E_PUT extends ChefOp
case object E_FOLD extends ChefOp
case object E_ADD extends ChefOp
case object E_REMOVE extends ChefOp
case object E_COMBINE extends ChefOp
case object E_DIVIDE extends ChefOp
case object E_ADDDRY extends ChefOp
case object E_LIQUIFY extends ChefOp
case object E_STIR extends ChefOp
case object E_MIX extends ChefOp
case object E_CLEAN extends ChefOp
case object E_POUR extends ChefOp
case object E_VERB extends ChefOp
case object E_SET extends ChefOp
case object E_SERVE extends ChefOp
case object E_REFRIGERATE extends ChefOp
case object E_SERVES extends ChefOp
